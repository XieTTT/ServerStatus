package run.serverstatus.app.utils.infoUtils;

import run.serverstatus.app.entities.info.TimedInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import oshi.software.os.OperatingSystem;

@Slf4j
@Component("infoPerHour")
public class TimedInfoUtil {

    /**
     * var
     */
    private final InfoUtil infoUtil;
    private final ConfigurableApplicationContext context;

    public TimedInfoUtil(InfoUtil infoUtil,
                         ConfigurableApplicationContext context
    ) {
        this.infoUtil = infoUtil;
        this.context = context;

    }

    /**
     * 获得每小时信息
     *
     * @return hourInfo
     */
    public TimedInfo getTimedInfo(int language, int size) {
        long s = System.currentTimeMillis();
        /*Get Timed Info Beans*/
        TimedInfo timedInfo = context.getBean("timedInfo", TimedInfo.class);
        /*Create Threads*/
        //Get Available Memory
        Thread got_available_memory_tread = new Thread(() -> {
            timedInfo.setAvailableMemory(infoUtil.availableMemory());
        }, "Ava Mem");

/*        //Get Fan Speed
        Thread got_fan_speed_thread = new Thread(() -> {
            timedInfo.setFanSpeed(infoUtil.fanSpeed(language));
        }, "FanSpeed");*/

        /*Get Process*/
        Thread got_processor_thread = new Thread(() -> {
            //Get Process Ordered By CPU
            infoUtil.ProcessOrder(OperatingSystem.ProcessSort.CPU, size, timedInfo);
            //Get Process Ordered By MEN
            infoUtil.ProcessOrder(OperatingSystem.ProcessSort.MEMORY, size, timedInfo);
        }, "Process");
        //cal time
        /*Start Threads*/
        got_available_memory_tread.start();
        /*got_fan_speed_thread.start();*/
        got_processor_thread.start();

        //Get SysTime
        timedInfo.setOsTime(infoUtil.osTime());
        //Get OsUptime
        timedInfo.setOsUptime(infoUtil.osUpTime());

        //wait threads
        try {
            /*Threads Join*/
            got_available_memory_tread.join();
           /* got_fan_speed_thread.join();*/
            got_processor_thread.join();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        log.info("getInfo spend: " + (System.currentTimeMillis() - s) + " ms");
        return timedInfo;
    }
}

