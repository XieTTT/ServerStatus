package run.serverstatus.app.controller;

import run.serverstatus.app.service.Impl.LineChartServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class GraphController {
    private final LineChartServiceImpl lineChartService;

    public GraphController(LineChartServiceImpl lineChartService) {
        this.lineChartService = lineChartService;
    }

    /**
     * Graph controller.
     *
     * @param map which has all lineChartInfo
     * @return View
     */
    @RequestMapping("/graph")
    public String graph(Map<String, Object> map) {
        Map<String, Object> infoMap = lineChartService.findAllArrayMin(61);
        map.put("cpuLoadArray", infoMap.get("cpuLoadArray"));
        map.put("cpuTempArray", infoMap.get("cpuTempArray"));
        map.put("netWorkSpeedInArray", infoMap.get("netWorkSpeedInArray"));
        map.put("netWorkSpeedOutArray", infoMap.get("netWorkSpeedOutArray"));
        return "graph";
    }
}
