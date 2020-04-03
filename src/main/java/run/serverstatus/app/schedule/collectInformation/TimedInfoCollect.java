package run.serverstatus.app.schedule.collectInformation;


import run.serverstatus.app.entities.Processor;
import run.serverstatus.app.entities.info.BootInfo;
import run.serverstatus.app.entities.info.LineChartInfo;
import run.serverstatus.app.entities.info.TimedInfo;
import run.serverstatus.app.entities.properties.Settings;
import run.serverstatus.app.repository.ProcessRepository;
import run.serverstatus.app.repository.PropertiesRepository;
import run.serverstatus.app.repository.info.BootInfoRepository;
import run.serverstatus.app.repository.info.LineChartRepository;
import run.serverstatus.app.repository.info.TimedInfoRepository;
import run.serverstatus.app.schedule.sendMail.SendTimedInfo;
import run.serverstatus.app.utils.infoUtils.TimedInfoUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Data
@Component
@EnableScheduling
public class TimedInfoCollect {
    /*A properties to set  email timed duration*/
    private Integer timedEmail = 0;

    private final TimedInfoUtil timedInfoUtil;
    private final TimedInfoRepository repository;
    private final PropertiesRepository pRepository;
    private final SendTimedInfo sendTimedInfo;
    private final LineChartRepository lRepository;
    private final BootInfoRepository bRepository;
    private final ProcessRepository proRepository;

    public TimedInfoCollect(TimedInfoUtil timedInfoUtil,
                            TimedInfoRepository repository,
                            PropertiesRepository pRepository,
                            SendTimedInfo sendTimedInfo,
                            LineChartRepository lRepository,
                            BootInfoRepository bRepository,
                            ProcessRepository proRepository) {
        this.timedInfoUtil = timedInfoUtil;
        this.repository = repository;
        this.pRepository = pRepository;
        this.sendTimedInfo = sendTimedInfo;
        this.lRepository = lRepository;
        this.bRepository = bRepository;
        this.proRepository = proRepository;
    }

    /* Timed info */
    @Scheduled(cron = "0 0,30 * * *  ?")
    public void timedCollectInformation() {
        Date date = new Date();
        if (timedEmail == 1) {
            //Every Half Hour
            startTask();
        } else if (timedEmail == 2) {
            //Every Hour
            String mm = new SimpleDateFormat("mm").format(date);
            if (Integer.parseInt(mm) == 0) {
                startTask();
            }
        } else if (timedEmail == 3) {
            //Every Half Day (00:00 12:00)
            String HH = new SimpleDateFormat("HH").format(date);
            if (Integer.parseInt(HH) == 12 || Integer.parseInt(HH) == 0) {
                startTask();
            }
        } else if (timedEmail == 4) {
            //Every Day (00:00)
            String HH = new SimpleDateFormat("HH").format(date);
            if (Integer.parseInt(HH) == 0) {
                startTask();
            }
        } else if (timedEmail == 0) {
            log.info("Timed Email has been turned off.");
        }
    }

    /* Send Timed Info ?*/
    public void startTask() {
        log.info("Timed Task: BEGIN");
        TimedInfo timedInfo = timedInfoCollect();
            BootInfo bootInfo = bRepository.findBootInfo();
            List<LineChartInfo> recentLineChartInfoHour = lRepository.findRecentLineChartInfoHour(1);
            LineChartInfo lineChartInfo = recentLineChartInfoHour.get(0);
            HashMap<String, Object> soMap = new HashMap<>();
            soMap.put("lineChartInfo", lineChartInfo);
            soMap.put("timedInfo", timedInfo);
            soMap.put("bootInfo", bootInfo);
            soMap.put("processorSortedByCPU", timedInfo.getProcessorsSortedByCPU());
            soMap.put("processorSortedByMEN", timedInfo.getProcessorsSortedByMEN());
            sendTimedInfo.sendTimedInfoByEmail(soMap);
        log.info("Timed Task: END");
    }

    public TimedInfo timedInfoCollect() {
        Settings settings = pRepository.findSettings();
        int lang;
        if (settings.getLanguage().equals("Chinese")) {
            lang = 0;
        } else if (settings.getLanguage().equals("English")) {
            lang = 1;
        } else {
            lang = 1;
            log.info("Language setting error, default Chinese ");
        }
        if (Integer.parseInt(settings.getProcessNum()) > 10) {
            log.info("ProcessNum must Not more than 10 ");
            settings.setProcessNum("10");
        }
        //Save info to database
        TimedInfo timedInfo = timedInfoUtil.getTimedInfo(lang, Integer.parseInt(settings.getProcessNum()));
        repository.insertTimedInfo(timedInfo);
        for (Processor processorsSortedByCPU : timedInfo.getProcessorsSortedByCPU()) {
            //Contact timedInfo and processInfo
            processorsSortedByCPU.setHourInfo_id(timedInfo.getId());
            proRepository.insertProcessorSortedByCPU(processorsSortedByCPU);
        }
        for (Processor processorsSortedByMEN : timedInfo.getProcessorsSortedByMEN()) {
            //Contact timedInfo and processInfo
            processorsSortedByMEN.setHourInfo_id(timedInfo.getId());
            proRepository.insertProcessorSortedByMEN(processorsSortedByMEN);
        }
        return timedInfo;
    }
}
