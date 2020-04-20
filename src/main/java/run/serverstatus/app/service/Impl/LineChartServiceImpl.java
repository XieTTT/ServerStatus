package run.serverstatus.app.service.Impl;

import run.serverstatus.app.entities.info.LineChartInfo;
import run.serverstatus.app.entities.info.NetworkSpeed;
import run.serverstatus.app.repository.info.LineChartRepository;
import run.serverstatus.app.service.LineChartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.*;

@Slf4j
@Service
public class LineChartServiceImpl implements LineChartService {

    private final LineChartRepository lineChartRepository;
    private final NetworkSpeed networkSpeed;

    public LineChartServiceImpl(LineChartRepository lineChartRepository,
                                NetworkSpeed networkSpeed) {
        this.lineChartRepository = lineChartRepository;
        this.networkSpeed = networkSpeed;
    }

    /**
     * Find CpuTemperature Info by repository
     *
     * @return list
     */
    @Override
    public Map<String, Object> findAllArrayMin(int limit) {
        List<LineChartInfo> lineChartInfo = lineChartRepository.findRecentLineChartInfoMinute(limit);
        List<double[]> cpuLoadArray = new LinkedList<>();
        List<double[]> cpuTempArray = new LinkedList<>();

        HashMap<String, Object> map = new HashMap<>();
        LinkedList<long[]> netWorkSpeedInArray = networkSpeed.getInm();
        LinkedList<long[]> netWorkSpeedOutArray = networkSpeed.getOutm();
        map.put("netWorkSpeedInArray", netWorkSpeedInArray);
        map.put("netWorkSpeedOutArray", netWorkSpeedOutArray);

        for (int i = lineChartInfo.size() - 1; i >= 0; i--) {
            double[] cpuLoad = new double[2];
            double[] cpuTemp = new double[2];
            cpuLoad[0] = lineChartInfo.size() - 1 - i;
            cpuLoad[1] = lineChartInfo.get(i).getCpuLoad();
            cpuTemp[0] = lineChartInfo.size() - 1 - i;
            cpuTemp[1] = lineChartInfo.get(i).getCpuTemperature();
            cpuLoadArray.add(cpuLoad);
            cpuTempArray.add(cpuTemp);
        }
        map.put("cpuLoadArray", cpuLoadArray);
        map.put("cpuTempArray", cpuTempArray);


        return map;
    }

    @Override
    public List<long[]> findNetWorkSpeedIn(String period) {
        switch (period) {
            case "last4min":
                return networkSpeed.getInm();
            case "last1hour":
                return networkSpeed.getInH();
            case "last24hours":
                return networkSpeed.getInD();
            case "lastMonth":
                return networkSpeed.getInM();
            default:
                return null;
        }
    }

    @Override
    public List<long[]> findNetWorkSpeedOut(String period) {
        switch (period) {
            case "last4min":
                return networkSpeed.getOutm();
            case "last1hour":
                return networkSpeed.getOutH();
            case "last24hours":
                return networkSpeed.getOutD();
            case "lastMonth":
                return networkSpeed.getOutM();
            default:
                return null;
        }
    }

    @Deprecated
    public List<List<Double>> findRecentCPUTempMinute() {
        List<LineChartInfo> recentCPUTempMinute = lineChartRepository.findRecentLineChartInfoMinute(30);
        List<List<Double>> cpuTemp = new LinkedList<>();
        for (LineChartInfo lineChartInfo : recentCPUTempMinute) {
            try {
                double cpuTemperature = lineChartInfo.getCpuTemperature();
                double longTime = lineChartInfo.getLongTime() + 1000 * 60 * 60 * 8L;/*+ 8 hours -> beijing time*/
                cpuTemp.add(Arrays.asList(longTime, cpuTemperature));
            } catch (NumberFormatException e) {
                log.info("LineChartServiceImpl.findRecentCPUTempMinute -> NumberFormatException......");
            }
        }
        return cpuTemp;
    }
}