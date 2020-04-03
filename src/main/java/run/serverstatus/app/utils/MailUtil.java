package run.serverstatus.app.utils;

import run.serverstatus.app.entities.properties.MailSettings;
import run.serverstatus.app.repository.PropertiesRepository;
import io.github.biezhi.ome.OhMyEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class MailUtil {

    private final PropertiesRepository repository;

    public MailUtil(PropertiesRepository repository) {
        this.repository = repository;
    }

    public boolean sendMail(String text, String title) {
        //加载配置信息
        long s = System.currentTimeMillis();
        MailSettings mailSettings = repository.findMailSettings();
        //If there is no recipient return false
        if (mailSettings.getTo().isEmpty()){
            return false;
        }
        upConfig(mailSettings);
        try {
            OhMyEmail
                    .subject(title)
                    .from(mailSettings.getMailSender())
                    .to(mailSettings.getTo())
                    .html(text)
                    .send();
            log.info("mail spend " + (System.currentTimeMillis() - s) + " ms");
        } catch (Exception e) {
            log.info("发送邮件到 " + mailSettings.getTo() + " 失败，请检查 SMTP 服务配置是否正确");
            return false;
        }
        return true;
    }

    private void upConfig(MailSettings mailSettings) {
        Properties properties = OhMyEmail.defaultConfig(false);
        properties.put("mail.smtp.host", mailSettings.getHost());
        OhMyEmail.config(properties, mailSettings.getUsername(), mailSettings.getPassword());
    }

/*    public static void main(String[] args) {
        boolean b = new MailUtil().sendMail("dsds", "dsdsd");
        System.out.println(b);
    }*/
}
