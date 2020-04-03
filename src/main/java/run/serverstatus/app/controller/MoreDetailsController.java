package run.serverstatus.app.controller;

import run.serverstatus.app.entities.info.BootInfo;
import run.serverstatus.app.entities.info.LineChartInfo;
import run.serverstatus.app.entities.info.TimedInfo;
import run.serverstatus.app.service.Impl.MoreDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public class MoreDetailsController {
    @Value("${serverstatus.dateFormat}")
    private String dataFormat;
    private final MoreDetailsServiceImpl moreDetailsService;


    public MoreDetailsController(MoreDetailsServiceImpl moreDetailsService) {
        this.moreDetailsService = moreDetailsService;
    }

    /**
     * Find info for html pages
     *
     * @param map to send values to html
     * @return View
     */
    @RequestMapping("moreDetails")
    public String moreDetails(Map<String, Object> map) {
        BootInfo bootInfo = moreDetailsService.findBootInfo();
        map.put("bootInfo", bootInfo);
        LineChartInfo lineChartInfoMin = moreDetailsService.findLineChartInfoMin();
        map.put("lineChartInfoMin", lineChartInfoMin);
        TimedInfo timedInfo = moreDetailsService.findTimedInfo();
        map.put("timedInfo", timedInfo);
        String osTime = new SimpleDateFormat(dataFormat).format(new Date());
        map.put("osTime", osTime);
        return "moreDetails";
    }

}
