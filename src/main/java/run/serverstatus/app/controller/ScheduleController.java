package run.serverstatus.app.controller;

import run.serverstatus.app.service.Impl.ScheduledServiceImpl;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
@Slf4j
@Controller
public class ScheduleController {

    private final ScheduledServiceImpl scheduledService;

    public ScheduleController(ScheduledServiceImpl scheduledService) {
        this.scheduledService = scheduledService;
    }

    @PostMapping("scheduleSettings")
    @ResponseBody
    public String schedule(HttpServletRequest request) {
        String timedDuration = request.getParameter("timedDuration");
        String temperatureWarning = request.getParameter("temperatureWarning");
        scheduledService.scheduleSettings(timedDuration, temperatureWarning);
        HashMap<String, Object> map = new HashMap<>();
        log.info("scheduleSettings -> timedDuration: "+timedDuration +"  ; temperatureWarning: "+temperatureWarning );
        map.put("flag", true);
        map.put("info", "scheduleSettings updated successfully ~");
        return JSON.toJSONString(map);
    }
}
