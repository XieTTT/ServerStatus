package run.serverstatus.app.schedule.collectInformation;

import run.serverstatus.app.Application;
import run.serverstatus.app.entities.info.LineChartInfo;
import run.serverstatus.app.repository.PropertiesRepository;
import run.serverstatus.app.repository.info.LineChartRepository;
import run.serverstatus.app.utils.MailUtil;
import run.serverstatus.app.utils.infoUtils.LineChartInfoUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Data
@EnableScheduling
public class LineChartInfoCollect {

    private final LineChartRepository lineChartRepository;
    private final LineChartInfoUtil lineChartInfoUtil;
    private final PropertiesRepository repository;
    private final MailUtil mailUtil;
    /*A properties to record the time of last waring email sending*/
    private Long longTime;
    /*A properties to set  waring email Threshold*/
    private Integer waringEmail = 0;

    public LineChartInfoCollect(LineChartRepository lineChartRepository,
                                LineChartInfoUtil lineChartInfoUtil,
                                PropertiesRepository repository,
                                MailUtil mailUtil) {
        this.lineChartRepository = lineChartRepository;
        this.lineChartInfoUtil = lineChartInfoUtil;
        this.repository = repository;
        this.mailUtil = mailUtil;
    }

    /*Collect info per minute*/
    @Scheduled(cron = "30 * * * * ?")
    public void minuteLineChartInfo() {
        startTask();
    }

    /*calculate info per hour*/
    @Scheduled(cron = "40 29,59 * * *  ?")
    public void hourLineChartInfo() {
        ConfigurableApplicationContext context = Application.getContext();
        LineChartInfo lineChartInfo = context.getBean("lineChartInfo", LineChartInfo.class);
        int totalMinuteInfo = lineChartRepository.findTotalMinuteInfo();
        if (totalMinuteInfo >= 60) {
            List<LineChartInfo> recentCPUTempMinute = lineChartRepository.findRecentLineChartInfoMinute(60);
            calcInfo(lineChartInfo, 60, recentCPUTempMinute);
            lineChartRepository.deleteExcessMinuteInfo((new Date().getTime() - 1000 * 60 * 60));
        } else if (totalMinuteInfo != 0) {
            List<LineChartInfo> recentCPUTempMinute = lineChartRepository.findRecentLineChartInfoMinute(totalMinuteInfo);
            calcInfo(lineChartInfo, totalMinuteInfo, recentCPUTempMinute);
        }
    }

    /**
     * Public so that AppBootTask can use it
     */
    public void startTask() {
        long s = System.currentTimeMillis();
        String language = repository.findSettings().getLanguage();
        int lang;
        if (language.equals("Chinese")) {
            lang = 0;
        } else if (language.equals("English")) {
            lang = 1;
        } else {
            lang = 0;
            log.info("Language setting error,default English.");
        }
        //Find information by infoUtil
        LineChartInfo lineChartInfo = lineChartInfoUtil.collectLineChartInfo(lang);
        this.lineChartRepository.insertMinuteInfo(lineChartInfo);
        log.info(lineChartInfo + "  Spend: " + (System.currentTimeMillis() - s) + " ms.");
        //send temperature waring email
        if (waringEmail != 0) {
            /*
             * 0 -> off
             */
            double cpuTemperature = lineChartInfo.getCpuTemperature();
            if (longTime != null && (System.currentTimeMillis() - longTime) < 1000 * 60 * 60) {
                return;
            }
            if (cpuTemperature > waringEmail) {
                mailUtil.sendMail("CPU Temperature Waring! " + cpuTemperature + "&#176;C", "Waring");
                this.longTime = System.currentTimeMillis();
            }
        }
    }

    /*calculate per hour*/
    private void calcInfo(LineChartInfo lineChartInfo, int totalMinuteInfo, List<LineChartInfo> recentCPUTempMinute) {
        double cpuTem = 0;
        double cpuLoad = 0;
        long internetSpeedIn = 0;
        long internetSpeedOut = 0;
        for (LineChartInfo lineChartInfo1 : recentCPUTempMinute) {
            cpuTem = lineChartInfo1.getCpuTemperature() + cpuTem;
            cpuLoad = lineChartInfo1.getCpuLoad() + cpuLoad;
            internetSpeedIn = lineChartInfo1.getInternetSpeedIn() + internetSpeedIn;
            internetSpeedOut = lineChartInfo1.getInternetSpeedOut() + internetSpeedOut;
        }
        lineChartInfo.setCpuTemperature(new BigDecimal(cpuTem / totalMinuteInfo).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
        lineChartInfo.setCpuLoad(new BigDecimal(cpuLoad / totalMinuteInfo).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
        lineChartInfo.setInternetSpeedIn(internetSpeedIn / totalMinuteInfo);
        lineChartInfo.setInternetSpeedOut(internetSpeedOut / totalMinuteInfo);
        lineChartInfo.setLongTime(new Date().getTime());
        lineChartRepository.insertHourInfo(lineChartInfo);
    }
}
