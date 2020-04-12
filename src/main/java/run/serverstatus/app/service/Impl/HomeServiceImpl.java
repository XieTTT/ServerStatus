package run.serverstatus.app.service.Impl;

import run.serverstatus.app.entities.info.Processor;
import run.serverstatus.app.entities.info.StaticInfo;
import run.serverstatus.app.entities.info.LineChartInfo;
import run.serverstatus.app.entities.info.TimedInfo;
import run.serverstatus.app.repository.ProcessRepository;
import run.serverstatus.app.repository.info.LineChartRepository;
import run.serverstatus.app.repository.info.TimedInfoRepository;
import run.serverstatus.app.service.HomeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {
    private final LineChartRepository lRepository;
    private final TimedInfoRepository tRepository;
    private final ProcessRepository pRepository;
    //Cache information with staticInfo
    private final StaticInfo staticInfo;

    public HomeServiceImpl(LineChartRepository lRepository,
                           TimedInfoRepository tRepository,
                           ProcessRepository pRepository,
                           StaticInfo staticInfo) {
        this.lRepository = lRepository;
        this.tRepository = tRepository;
        this.pRepository = pRepository;
        this.staticInfo = staticInfo;
    }

    @Override
    public HashMap<String, Object> findShowInfo() {
        HashMap<String, Object> map = new HashMap<>();
        List<LineChartInfo> lineChartInfoMinute = lRepository.findRecentLineChartInfoMinute(30);
        List<TimedInfo> recentTimedInfo = tRepository.findRecentTimedInfo(1);
        TimedInfo timedInfo = recentTimedInfo.get(0);
        map.put("bootInfo", staticInfo);
        map.put("timedInfo", timedInfo);
        map.put("lineChartInfoMinute", lineChartInfoMinute);
        return map;
    }

    @Override
    public List<double[]> findCPULoadArray(int limit) {
        List<double[]> doubles = new LinkedList<>();
        List<LineChartInfo> cpuLoadList = lRepository.findRecentLineChartInfoMinuteCPULoad(limit);
        for (int i = cpuLoadList.size() - 1; i >= 0; i--) {
            double[] cpuLoad = new double[2];
            cpuLoad[0] = cpuLoadList.size() - 1 - i;
            cpuLoad[1] = cpuLoadList.get(i).getCpuLoad();
            doubles.add(cpuLoad);
        }
        return doubles;
    }

    @Override
    public List<Processor> findProcessSortedByCPU(int id) {
        return pRepository.findProcessorSortedByCPU(id);
    }

    @Override
    public List<Processor> findProcessSortedByMEN(int id) {
        return pRepository.findProcessorSortedByMEN(id);

    }

    @Override
    public TimedInfo findLastTimedInfo() {
        return tRepository.findRecentTimedInfo(1).get(0);

    }
}
