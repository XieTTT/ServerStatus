package run.serverstatus.app.schedule;

import run.serverstatus.app.config.properties.ServerStatusProperties;
import run.serverstatus.app.entities.properties.Account;
import run.serverstatus.app.entities.properties.MailSettings;
import run.serverstatus.app.entities.properties.AppSettings;
import run.serverstatus.app.repository.PropertiesRepository;
import run.serverstatus.app.schedule.collectInformation.LineChartInfoCollect;
import run.serverstatus.app.schedule.collectInformation.TimedInfoCollect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import run.serverstatus.app.utils.infoUtils.StaticInfoUtil;

@Slf4j
@Component
public class AppBootTask implements ApplicationRunner {


    private final ServerStatusProperties serverStatusProperties;
    private final PropertiesRepository propertiesRepository;
    private final MailSettings mailSettings;
    private final Account account;
    private final AppSettings appSettings;
    private final TimedInfoCollect tCollect;
    private final LineChartInfoCollect lineChartInfoCollect;
    private final StaticInfoUtil staticInfoUtil;

    public AppBootTask(ServerStatusProperties serverStatusProperties,
                       PropertiesRepository propertiesRepository,
                       MailSettings mailSettings,
                       Account account,
                       AppSettings appSettings,
                       TimedInfoCollect tCollect,
                       LineChartInfoCollect lineChartInfoCollect,
                       StaticInfoUtil staticInfoUtil) {
        this.serverStatusProperties = serverStatusProperties;
        this.propertiesRepository = propertiesRepository;
        this.mailSettings = mailSettings;
        this.account = account;
        this.appSettings = appSettings;
        this.tCollect = tCollect;
        this.lineChartInfoCollect = lineChartInfoCollect;
        this.staticInfoUtil = staticInfoUtil;
    }

    @Override
    public void run(ApplicationArguments args) {
        long s = System.currentTimeMillis();
        log.info("ApplicationRunner Begins.");
        log.info("Insert properties to database");
        appSettings.setLanguage(serverStatusProperties.getLanguage());
        appSettings.setServerName(serverStatusProperties.getServerName());
        appSettings.setMark(serverStatusProperties.getMark());
        appSettings.setProcessNum(serverStatusProperties.getProcessNum());
        //Get values from map
        mailSettings.setMailSender(serverStatusProperties.getMail().get("from"));
        mailSettings.setHost(serverStatusProperties.getMail().get("host"));
        mailSettings.setUsername(serverStatusProperties.getMail().get("username"));
        mailSettings.setPassword(serverStatusProperties.getMail().get("password"));
        mailSettings.setTo(serverStatusProperties.getMail().get("to"));
        if (serverStatusProperties.getAccount() != null) {
            account.setPassword(serverStatusProperties.getAccount().get("password"));
            account.setUsername(serverStatusProperties.getAccount().get("username"));
            propertiesRepository.insertAccount(account);
        }
        /*Insert Settings to dataBase*/
        propertiesRepository.insertSettings(appSettings);
        propertiesRepository.insertMail(mailSettings);
        /*Insert a timedInfo to database*/
        tCollect.timedInfoCollect();
        /*Insert lineChartInfo to database*/
        lineChartInfoCollect.startTask();
        /*Cache information with staticInfo*/
        staticInfoUtil.collectStaticInfo();
        log.info("ApplicationRunner Ends. Spend: " + (System.currentTimeMillis() - s) + "ms.");
    }
}