package run.serverstatus.app.controller;

import run.serverstatus.app.entities.properties.MailSettings;
import run.serverstatus.app.entities.properties.AppSettings;
import run.serverstatus.app.service.Impl.SettingsServiceImpl;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Slf4j
@Controller
public class SettingsController {
    private final MailSettings mailSettings;
    private final AppSettings appSettings;
    private final SettingsServiceImpl settingsService;

    public SettingsController(MailSettings mailSettings,
                              AppSettings appSettings,
                              SettingsServiceImpl settingsService) {
        this.mailSettings = mailSettings;
        this.appSettings = appSettings;
        this.settingsService = settingsService;
    }

    @RequestMapping("/settings")
    public String settings() {
        return "settings";
    }

    /**
     * appSettings controller
     *
     * @param request HttpServletRequest
     * @return json
     */
    @PostMapping("appSettings")
    @ResponseBody
    public String settings(HttpServletRequest request) {
        appSettings.setServerName(request.getParameter("serverName"));
        appSettings.setMark(request.getParameter("mark"));
        appSettings.setLanguage(request.getParameter("language"));
        appSettings.setProcessNum(request.getParameter("processNum"));
        boolean flag = settingsService.updateSettings(appSettings);
        HashMap<String, Object> map = new HashMap<>();
        map.put("flag", flag);
        if (flag) {
            map.put("info", "appSettings updated successfully ~");
        }
        return JSON.toJSONString(map);
    }

    /**
     * mailSettings controller
     *
     * @param request HttpServletRequest
     * @return json
     */
    @PostMapping("mailSettings")
    @ResponseBody
    public String mailSettings(HttpServletRequest request) {
        mailSettings.setMailSender(request.getParameter("senderName"));
        mailSettings.setTo(request.getParameter("recipient"));
        mailSettings.setPassword(request.getParameter("aPassword"));
        mailSettings.setUsername(request.getParameter("sender"));
        mailSettings.setHost(request.getParameter("host"));
        boolean flag = settingsService.updateMailSettings(mailSettings);
        HashMap<String, Object> map = new HashMap<>();
        map.put("flag", flag);
        if (flag) {
            map.put("info", "maiSettings updated successfully ~");
        }
        return JSON.toJSONString(map);
    }

    /**
     * Send text email controller
     *
     * @param request HttpServletRequest
     * @return json
     */
    @GetMapping("mailTesting")
    @ResponseBody
    public String sendMail(HttpServletRequest request) {
        String content = request.getParameter("content");
        log.info("Test mail: " + content);
        boolean flag = settingsService.sendTestMail(content);
        HashMap<String, Object> map = new HashMap<>();
        map.put("flag", flag);
        if (flag) {
            if (content.length() > 6) {
                content = content.substring(0, 6);
                map.put("info", "Successfully Send mail: " + content + "  ....");
            } else {
                map.put("info", "Successfully Send mail: " + content);
            }
        }
        return JSON.toJSONString(map);
    }
}