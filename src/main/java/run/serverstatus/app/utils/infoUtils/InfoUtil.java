package run.serverstatus.app.utils.infoUtils;

import run.serverstatus.app.config.properties.ServerStatusProperties;
import run.serverstatus.app.entities.info.TimedInfo;
import run.serverstatus.app.entities.Processor;
import run.serverstatus.app.repository.PropertiesRepository;
import run.serverstatus.app.utils.HttpClientUtil;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Cpu;
import com.profesorfalken.jsensors.model.sensors.Temperature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import oshi.PlatformEnum;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import oshi.util.Util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@Component
public class InfoUtil {

    private final PropertiesRepository repository;
    private final HttpClientUtil httpClientUtil;
    private final OperatingSystem operatingSystem;
    private final HardwareAbstractionLayer hardWard;
    private final NetworkIF[] networkIFS;
    private final ServerStatusProperties properties;
    private final ConfigurableApplicationContext context;

    public InfoUtil(PropertiesRepository repository,
                    HttpClientUtil httpClientUtil,
                    OperatingSystem operatingSystem,
                    HardwareAbstractionLayer hardWard,
                    NetworkIF[] networkIFS,
                    ServerStatusProperties properties,
                    ConfigurableApplicationContext context) {
        this.repository = repository;
        this.httpClientUtil = httpClientUtil;
        this.operatingSystem = operatingSystem;
        this.hardWard = hardWard;
        this.networkIFS = networkIFS;
        this.properties = properties;
        this.context=context;
    }

    //Got Long time
    public Long longTime() {
        log.info("Got LongTime");
        return new Date().getTime();
    }

    //Get publicIp
    public String publicIp() {
        long s = System.currentTimeMillis();
        String info = httpClientUtil.doGet("http://whatismyip.akamai.com/");
        log.info("Got IP: " + info + " By: http://whatismyip.akamai.com/" + "spend: " +
                (System.currentTimeMillis() - s) + " ms");
        return info;
    }

    //Get getIntranetIp
    public String IntranetIp() {
        long s = System.currentTimeMillis();
        try {
            Enumeration<NetworkInterface> allNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
            for (; allNetworkInterfaces.hasMoreElements(); ) {
                NetworkInterface networkInterface = allNetworkInterfaces.nextElement();
                if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp() || networkInterface.getDisplayName().contains("VM")) {
                    continue;
                }
                for (Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses(); inetAddressEnumeration.hasMoreElements(); ) {
                    InetAddress inetAddress = inetAddressEnumeration.nextElement();
                    //如果此IP不为空
                    if (inetAddress != null) {
                        //如果此IP为IPV4 则返回
                        String hostAddress = inetAddress.getHostAddress();
                        if (inetAddress instanceof Inet4Address) {
                            log.info("Got IP: " + hostAddress + " spend: " +
                                    (System.currentTimeMillis() - s) + " ms");
                            return hostAddress;
                        }
                    }
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Get CpuLoad
    public double cpuLoad() {
        long s = System.currentTimeMillis();
        CentralProcessor cProcessor = hardWard.getProcessor();
        long[] processorCpuLoadTicks = cProcessor.getSystemCpuLoadTicks();
        Util.sleep(1000);
        long[] ticks = cProcessor.getSystemCpuLoadTicks();
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - processorCpuLoadTicks[CentralProcessor.TickType.USER.getIndex()];
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - processorCpuLoadTicks[CentralProcessor.TickType.NICE.getIndex()];
        long sys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - processorCpuLoadTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - processorCpuLoadTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long ioWait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - processorCpuLoadTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - processorCpuLoadTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softIrq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - processorCpuLoadTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - processorCpuLoadTicks[CentralProcessor.TickType.STEAL.getIndex()];
        double info = cProcessor.getSystemCpuLoadBetweenTicks(processorCpuLoadTicks) * 100;
        log.info("Got CpuLoad: " + String.format("%.1f%%", cProcessor.getSystemCpuLoadBetweenTicks(processorCpuLoadTicks)
                * 100) + " spend: " + (System.currentTimeMillis() - s) + " ms");
        return info;
    }

    //Get Available Memory
    public String availableMemory() {
        long s = System.currentTimeMillis();
        GlobalMemory memory = hardWard.getMemory();
        double total = memory.getTotal();
        double available = memory.getAvailable();
        if (total < 1073741824/*1g*/) {
            String info = String.format("%.2f", available / 1024 / 1024) + "MB" + "/" +
                    String.format("%.2f", total / 1024 / 1024) + "MB";
            log.info("Got Available Memory " + " spend: " + (System.currentTimeMillis() - s) + " ms");
            return info;
        } else {
            String info = String.format("%.2f", available / 1024 / 1024 / 1024) + "GB" + "/" +
                    String.format("%.2f", total / 1024 / 1024 / 1024) + "GB";
            log.info("Got Available Memory " + " spend: " + (System.currentTimeMillis() - s) + " ms");
            return info;
        }
    }

    /**
     * JSensors-> windows    oshi.sensor -> linux
     *
     * @return Temperature
     */
    public double cpuTemperature(int language) {
        long s = System.currentTimeMillis();
        Sensors sensors = hardWard.getSensors();
        if (SystemInfo.getCurrentPlatformEnum() == PlatformEnum.WINDOWS) {
            Components components = JSensors.get.components();
            List<Cpu> cpus = components.cpus;
            Double maxTemp = 0d;
            for (final Cpu cpu : cpus) {
                List<Temperature> temperatures = cpu.sensors.temperatures;
                Temperature temperature = temperatures.get(temperatures.size() - 1);
                Double value = temperature.value;
                if (value > maxTemp) {
                    maxTemp = value;
                }
            }
            if (maxTemp == 0.0) {
                if (language == 0) {
                    log.info("需要管理员权限 " + " spend: " + (System.currentTimeMillis() - s) + " ms");
                } else {
                    log.info("Failed to Get CPU Temperature,Need Administrator Mode " + " spend: " +
                            (System.currentTimeMillis() - s) + " ms");
                }
                return -1;

            }
            log.info("Got CPU Temperature " + " spend: " + (System.currentTimeMillis() - s) + " ms");
            return maxTemp;
        } else if (sensors.getCpuTemperature() < 0.1 || sensors.getCpuTemperature() == 0) {
            if (language == 0) {
                log.info("获取温度失败");
            } else {
                log.info("Failed to Get CPU Temperature");
            }
            return -1;
        } else {
            log.info("Got CPU Temperature " + " spend: " + (System.currentTimeMillis() - s) + " ms");
            return sensors.getCpuTemperature();
        }
    }


    //Get Fan Speed
    public long fanSpeed(int language) {
        long s = System.currentTimeMillis();
        boolean flag = false;
        int maxFanSpeed = 0;
        for (int fanSpeed : hardWard.getSensors().getFanSpeeds()) {
            flag = true;
            if (maxFanSpeed < fanSpeed)
                maxFanSpeed = fanSpeed;
        }
        if (flag) {
            log.info("Got Fan Speed" + (System.currentTimeMillis() - s) + " ms");
            return maxFanSpeed;
        } else {
            if (language == 0) {
                log.info("获取风扇转速失败" + (System.currentTimeMillis() - s) + " ms");
            } else {
                log.info("Failed Getting FanSpeed... " + " spend: " + (System.currentTimeMillis() - s) + " ms");
            }
            return -1;
        }
    }

    //Get NetworkTraffic
    public List<Long> networkTraffic(int language) {
        long s = System.currentTimeMillis();
        boolean flag = false;
        long bytesSent = 0;
        long bytesRecv = 0;
        for (NetworkIF networkIF : networkIFS) {
            if (networkIF.getIPv4addr().length < 1) {
                continue;
            }
            flag = true;
            long bytesSent1 = networkIF.getBytesSent();
            long bytesRecv1 = networkIF.getBytesRecv();
            Util.sleep(1000);
            networkIF.updateAttributes();
            long bytesSent2 = networkIF.getBytesSent();
            long bytesRecv2 = networkIF.getBytesRecv();
            bytesSent = (bytesSent2 - bytesSent1) / 1024;
            bytesRecv = (bytesRecv2 - bytesRecv1) / 1024;
        }
        if (!flag) {
            if (language == 0) {
                log.info("获取网速失败  ..." + (System.currentTimeMillis() - s) + " ms");
            } else {
                log.info("failed to get networkTraffic..." + (System.currentTimeMillis() - s) + " ms");
            }
            return Arrays.asList(-1L, -1L);
        }
        log.info("Got NetworkTraffic" + " spend: " + (System.currentTimeMillis() - s) + " ms");
        return Arrays.asList(bytesRecv, bytesSent);
    }

    /*Get Process*/
    public void ProcessOrder(OperatingSystem.ProcessSort sort, int size, TimedInfo timedInfo) {
        long s = System.currentTimeMillis();
        String sortStr = sort.toString();
        OSProcess[] processes = operatingSystem.getProcesses(size, sort);
        for (int i = 0; i < processes.length; i++) {
            OSProcess process = processes[i];
            Processor processor = context.getBean("processor", Processor.class);
            processor.setSortID(i);
            processor.setOrderBy(sortStr);
            processor.setPID(Integer.toString(process.getProcessID()));
            processor.setCPU(calCPUPercent(process));
            processor.setMEN(calMENPercent(process));
            processor.setVSZ(getMemoryVSZTextSize(process));
            processor.setRSS(getMemoryRSSTextSize(process));
            processor.setName(process.getName());
            if (sortStr.equals("CPU")) {
                timedInfo.addProcessorsSortedByCPU(processor);
            } else if (sortStr.equals("MEMORY")) {
                timedInfo.addProcessorsSortedByMEN(processor);
            }
        }
        log.info("Got Process Ordered:" + sortStr + " spend: " + (System.currentTimeMillis() - s) + " ms");
    }

    //Get hostName
    public String hostName() {
        log.info("Got hostName");
        return repository.findSettings().getHostName();
    }

    //Get OsName
    public String osName() {
        log.info("Got OsName");
        return System.getProperty("os.name");
    }

    //Get OsTime
    public String osTime() {
        log.info("Got OsTime");
        return new SimpleDateFormat(properties.getDateFormat()).format(new Date());
    }

    //Get OsBootTime
    public String osBootTime() {
        log.info("Got OsBootTime");
        long systemBootTime = operatingSystem.getSystemBootTime();
        return new SimpleDateFormat(properties.getDateFormat()).format(new Date(systemBootTime * 1000L));

    }

    //Get OsUptime
    public String osUpTime() {
        log.info("Got OsUpTime");
        return FormatUtil.formatElapsedSecs(operatingSystem.getSystemUptime());
    }

    /**
     * 计算 CPU 占用率
     * @param  process OSProcess
     * @return info
     */
    private String calCPUPercent(OSProcess process) {
        long min = (process.getKernelTime() + process.getUserTime());
        long max = process.getUpTime();
        return String.format("%.2f", ((double) min / (double) max) * 100) + "%";
    }

    /*计算 MEN 占用率*/
    private String calMENPercent(OSProcess process) {
        long min = process.getResidentSetSize();
        long max = hardWard.getMemory().getTotal();
        return String.format("%.2f", ((double) min / (double) max) * 100) + "%";
    }

    /*Get VSZ*/
    private String getMemoryVSZTextSize(OSProcess process) {
        long size = process.getVirtualSize();
        return getMemoryTestSize(size);
    }

    /*Get RsSS*/
    private String getMemoryRSSTextSize(OSProcess process) {
        long size = process.getResidentSetSize();
        return getMemoryTestSize(size);
    }

    /*Get MemoryTestSize*/
    private String getMemoryTestSize(long size) {
        StringBuilder sizeBuffer = new StringBuilder();
        if (size > 1073741824) {
            sizeBuffer.append(String.format("%.1f", (double) size / 1024 / 1024 / 1024)).append("G");
        } else if (size < 1073741824 && size > 1048576) {
            sizeBuffer.append(String.format("%.1f", (double) size / 1024 / 1024)).append("M");
        } else {
            sizeBuffer.append(String.format("%.1f", (double) size / 1024)).append("K");
        }
        return sizeBuffer.toString();
    }

}