package run.serverstatus.app.service.Impl;

import run.serverstatus.app.entities.properties.MailSettings;
import run.serverstatus.app.entities.properties.AppSettings;
import run.serverstatus.app.repository.PropertiesRepository;
import run.serverstatus.app.service.SettingsService;
import run.serverstatus.app.utils.MailUtil;
import org.springframework.stereotype.Service;

@Service
public class SettingsServiceImpl implements SettingsService {

    private final PropertiesRepository propertiesRep;
    private final MailUtil mailUtil;

    public SettingsServiceImpl(PropertiesRepository propertiesRep,
                               MailUtil mailUtil) {
        this.propertiesRep = propertiesRep;
        this.mailUtil = mailUtil;
    }

    /**
     * Update application settings from webPages
     *
     * @param appSettings form Controller
     * @return boolean
     */
    @Override
    public boolean updateSettings(AppSettings appSettings) {
        int i = propertiesRep.updateSettings(appSettings);
        return i == 1;
    }

    /**
     * Update mailSettings from webPages
     *
     * @param mailSettings from webPages
     * @return boolean
     */
    @Override
    public boolean updateMailSettings(MailSettings mailSettings) {
        int i = propertiesRep.updateMailSettings(mailSettings);
        return i == 1;
    }

    /**
     * Send test mail
     *
     * @param content test mail
     */
    @Override
    public boolean sendTestMail(String content) {
        return mailUtil.sendMail(content, "TestingMail");

    }
}
