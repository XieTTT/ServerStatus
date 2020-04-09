package run.serverstatus.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.*;

import java.util.Arrays;

@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        Sensors sensors = hardware.getSensors();
        double cpuVoltage = sensors.getCpuVoltage();
        System.out.println(cpuVoltage);
    }


    public static void main(String[] args) {
        System.out.println("----");
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        ComputerSystem computerSystem = hardware.getComputerSystem();
        Baseboard baseboard = computerSystem.getBaseboard();
/*        Baseboard baseboard = computerSystem.getBaseboard();
        String manufacturer = baseboard.getManufacturer();
        String model = baseboard.getModel();
        String serialNumber = baseboard.getSerialNumber();
        String version = baseboard.getVersion();*/
        HWDiskStore[] diskStores = hardware.getDiskStores();
/*        for (HWDiskStore diskStore : diskStores) {
            long writeBytes = diskStore.getWriteBytes();

            long reads = diskStore.getReads();
            System.out.println(reads);
            System.out.println(writeBytes);
            String string = diskStore.toString();
            System.out.println(string);
            long currentQueueLength = diskStore.getCurrentQueueLength();
            System.out.println(currentQueueLength);
        }*/
        CentralProcessor processor = hardware.getProcessor();
        double[] systemLoadAverage = processor.getSystemLoadAverage(3);
        CentralProcessor.LogicalProcessor[] logicalProcessors = processor.getLogicalProcessors();
   /*     for (CentralProcessor.LogicalProcessor logicalProcessor : logicalProcessors) {
            int processorNumber = logicalProcessor.getProcessorNumber();
            int processorGroup = logicalProcessor.getProcessorGroup();
            int numaNode = logicalProcessor.getNumaNode();

            System.out.println(processorNumber+processorGroup+numaNode);
        }
        System.out.println(Arrays.toString(systemLoadAverage));*/
        System.out.println("=====================================================");
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        OSProcess[] processes = operatingSystem.getProcesses(5, OperatingSystem.ProcessSort.CPU);
        for (OSProcess process : processes) {
            long residentSetSize = process.getResidentSetSize();
            System.out.println(residentSetSize);
            long kernelTime = process.getKernelTime();
            System.out.println(kernelTime);
            long upTime = process.getUpTime();
            System.out.println(upTime);
            long userTime = process.getUserTime();
            System.out.println("userTime" + userTime);
            long virtualSize = process.getVirtualSize();
            System.out.println("virtualSize" + virtualSize);
            String user = process.getUser();
            System.out.println("user" + user);
            double v = process.calculateCpuPercent();
            System.out.println("calculateCpuPercent" + v);
            long startTime = process.getStartTime();
            System.out.println("startTime" + startTime);
            int threadCount = process.getThreadCount();
            System.out.println("threadCount" + threadCount);
            String string = process.toString();
            System.out.println(string);
        }
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        String manufacturer = operatingSystem.getManufacturer();
        System.out.println("manufacturer" + manufacturer);
        int threadCount = operatingSystem.getThreadCount();
        System.out.println(threadCount);
        boolean elevated = operatingSystem.isElevated();
        System.out.println(elevated);
        NetworkParams networkParams = operatingSystem.getNetworkParams();
        String hostName = networkParams.getHostName();
        System.out.println("hostname" + hostName);
        String domainName = networkParams.getDomainName();
        System.out.println("domainName" + domainName);
        String ipv4DefaultGateway = networkParams.getIpv4DefaultGateway();
        System.out.println("ip4" + ipv4DefaultGateway);
        String[] dnsServers = networkParams.getDnsServers();
        System.out.println(Arrays.toString(dnsServers));
        System.out.println("+++++++++++++++++++++++++++++++++++++");
        FileSystem fileSystem = operatingSystem.getFileSystem();
        OSFileStore[] fileStores = fileSystem.getFileStores();
        for (OSFileStore fileStore : fileStores) {
            String mount = fileStore.getMount();
            long freeSpace = fileStore.getFreeSpace();
            System.out.println(freeSpace);
            System.out.println("m" + mount);
        }
    }
}

