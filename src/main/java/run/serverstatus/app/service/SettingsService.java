package run.serverstatus.app.service;

import run.serverstatus.app.entities.properties.MailSettings;
import run.serverstatus.app.entities.properties.AppSettings;

public interface SettingsService {
    /**
     * Update application settings from webPages
     *
     * @param appSettings form Controller
     * @return boolean
     */
    boolean updateSettings(AppSettings appSettings);

    /**
     * Update mailSettings from webPages
     *
     * @param mailSettings from webPages
     * @return boolean
     */
    boolean updateMailSettings(MailSettings mailSettings);

    /**
     * Send test mail
     *
     * @param content test mail
     */
    boolean sendTestMail(String content);

}
