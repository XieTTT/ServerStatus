package run.serverstatus.app.utils.infoUtils;

import lombok.extern.slf4j.Slf4j;
import run.serverstatus.app.entities.info.LineChartInfo;
import org.springframework.stereotype.Component;
import run.serverstatus.app.utils.infoUtils.baseUtil.InfoUtil;

import java.util.List;

@Slf4j
@Component("infoPerMinute")
public class LineChartInfoUtil {

    private final InfoUtil infoUtil;
    private final LineChartInfo lineChartInfo;

    public LineChartInfoUtil(InfoUtil infoUtil,
                             LineChartInfo lineChartInfo) {

        this.infoUtil = infoUtil;
        this.lineChartInfo = lineChartInfo;
    }


    public LineChartInfo collectLineChartInfo(int language) {
        //Get time
        lineChartInfo.setLongTime(infoUtil.longTime());

        /*Create Threads*/
        //Get CpuLoad
        Thread got_cpuLoad_thread = new Thread(() -> {
            double value = infoUtil.cpuLoad();
            //Keep two decimal places
            value = (double) Math.round(value * 100) / 100;
            lineChartInfo.setCpuLoad(value);
        }, "CpuLoad");

        //Get CPU Temperature
        Thread got_cpu_temperature_thread = new Thread(() -> {
            lineChartInfo.setCpuTemperature(infoUtil.cpuTemperature(language));
        }, "CPU Tem");

/*        //Get networkSpeed
        Thread got_networkTraffic_thread = new Thread(() -> {
            List<Long> infos = infoUtil.networkTraffic();
            lineChartInfo.setInternetSpeedIn(infos.get(0));
            lineChartInfo.setInternetSpeedOut(infos.get(1));

        }, "NetTraffic");*/
        //Start thread
        got_cpuLoad_thread.start();
        got_cpu_temperature_thread.start();
/*        got_networkTraffic_thread.start();*/

        try {
            //Jon main thread
            got_cpuLoad_thread.join();
            got_cpu_temperature_thread.join();
/*            got_networkTraffic_thread.join();*/
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return lineChartInfo;
    }
}
