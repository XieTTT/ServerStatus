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
        Map<String, List<double[]>> infoMap = lineChartService.findAllArrayMin(61);
        List<double[]> cpuLoadArray = infoMap.get("cpuLoadArray");
        map.put("cpuLoad", cpuLoadArray);
        List<double[]> cpuTempArray = infoMap.get("cpuTempArray");
        map.put("cpuTemp", cpuTempArray);
        List<double[]> netWorkSpeedInArray = infoMap.get("netWorkSpeedInArray");
        List<double[]> netWorkSpeedOutArray = infoMap.get("netWorkSpeedOutArray");
        map.put("netWorkSpeedInArray", netWorkSpeedInArray);
        map.put("netWorkSpeedOutArray", netWorkSpeedOutArray);
        return "graph";
    }
}
