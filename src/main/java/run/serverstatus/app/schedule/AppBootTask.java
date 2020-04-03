package run.serverstatus.app.schedule;

import run.serverstatus.app.config.properties.ServerStatusProperties;
import run.serverstatus.app.entities.properties.Account;
import run.serverstatus.app.entities.properties.MailSettings;
import run.serverstatus.app.entities.properties.Settings;
import run.serverstatus.app.repository.PropertiesRepository;
import run.serverstatus.app.repository.info.BootInfoRepository;
import run.serverstatus.app.schedule.collectInformation.LineChartInfoCollect;
import run.serverstatus.app.schedule.collectInformation.TimedInfoCollect;
import run.serverstatus.app.utils.infoUtils.BootInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppBootTask implements ApplicationRunner {


    private final ServerStatusProperties serverStatusProperties;
    private final PropertiesRepository propertiesRepository;
    private final MailSettings mailSettings;
    private final Account account;
    private final Settings settings;
    private final TimedInfoCollect tCollect;
    private final LineChartInfoCollect lineChartInfoCollect;

    public AppBootTask(ServerStatusProperties serverStatusProperties,
                       PropertiesRepository propertiesRepository,
                       MailSettings mailSettings,
                       Account account,
                       Settings settings,
                       TimedInfoCollect tCollect,
                       LineChartInfoCollect lineChartInfoCollect) {
        this.serverStatusProperties = serverStatusProperties;
        this.propertiesRepository = propertiesRepository;
        this.mailSettings = mailSettings;
        this.account = account;
        this.settings = settings;
        this.tCollect = tCollect;
        this.lineChartInfoCollect= lineChartInfoCollect;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("ApplicationRunner Begins.");
        log.info("Insert properties to database");
        settings.setHostName(serverStatusProperties.getHostName());
        settings.setLanguage(serverStatusProperties.getLanguage());
        settings.setMark(serverStatusProperties.getMark());
        settings.setProcessNum(serverStatusProperties.getProcessNum());
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
        propertiesRepository.insertSettings(settings);
        propertiesRepository.insertMail(mailSettings);
        /*Insert a timedInfo to database*/
        tCollect.timedInfoCollect();
        /*Insert lineChartInfo to database*/

        /*Insert lineChartInfo to database*/
        lineChartInfoCollect.startTask();
        log.info("ApplicationRunner Ends.");
    }
}