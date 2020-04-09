package run.serverstatus.app.schedule.sendMail;

import run.serverstatus.app.entities.info.StaticInfo;
import run.serverstatus.app.utils.MailUtil;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class SendBootInformation {
    private final TemplateEngine templateEngine;

    private final MailUtil mailUtil;
    private final Context context;

    public SendBootInformation(TemplateEngine templateEngine,
                               MailUtil mailUtil,
                               Context context) {
        this.templateEngine = templateEngine;
        this.mailUtil = mailUtil;
        this.context = context;
    }

    /**
     * 通过邮件发送 os 启动信息
     *
     * @return 是否发送成功
     */
    public boolean sendBootInfoByEmail(StaticInfo staticInfo) {
        context.setVariable("staticInfo", staticInfo);
        String htmlEmail = templateEngine.process("BootInformation", context);
        return mailUtil.sendMail(htmlEmail, "BootInfo");
    }
}

