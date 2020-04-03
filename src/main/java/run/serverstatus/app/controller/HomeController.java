package run.serverstatus.app.controller;

import run.serverstatus.app.entities.Processor;
import run.serverstatus.app.entities.info.TimedInfo;
import run.serverstatus.app.service.Impl.HomeServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    private final HomeServiceImpl dashboardService;

    public HomeController(HomeServiceImpl dashboardService) {
        this.dashboardService = dashboardService;
    }

    @RequestMapping("/home")
    public String home(Map<String, Object> map) {
        List<double[]> cpuLoadArray = dashboardService.findCPULoadArray(61);
        map.put("cpuLoadArray", cpuLoadArray);
        TimedInfo lastTInfo = dashboardService.findLastTimedInfo();
        List<Processor> processSortedByCPU = dashboardService.findProcessSortedByCPU(lastTInfo.getId());
        List<Processor> processSortedByMEN = dashboardService.findProcessSortedByMEN(lastTInfo.getId());
        map.put("processSortedByMEN", processSortedByMEN);
        map.put("processSortedByCPU", processSortedByCPU);
/*        List<TimedInfo> recentTimedInfo = timedInfoRepository.findRecentTimedInfo(1);
        List<Processor> processorSortedByCPU = processRepository.findProcessorSortedByCPU(recentTimedInfo.get(0).getId());
        map.put("hourInfo", recentTimedInfo);
        map.put("processorSortedByCPU", processorSortedByCPU);*/
        return "home";
    }
}
