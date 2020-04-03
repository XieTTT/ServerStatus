package run.serverstatus.app.schedule.sendMail;

import run.serverstatus.app.utils.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;


@Slf4j
@Component
public class SendTimedInfo {
    private final MailUtil mailUtil;
    private final TemplateEngine templateEngine;

    public SendTimedInfo(MailUtil mailUtil,
                         TemplateEngine templateEngine) {
        this.mailUtil = mailUtil;
        this.templateEngine = templateEngine;
    }

    public void sendTimedInfoByEmail(HashMap<String, Object> soMap) {
        Context context = new Context();
        context.setVariables(soMap);
        String text = templateEngine.process("TimedInfo", context);
        mailUtil.sendMail(text, "TimedInfo");
    }
}

