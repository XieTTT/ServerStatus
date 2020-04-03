package run.serverstatus.app.service.Impl;

import run.serverstatus.app.entities.info.LineChartInfo;
import run.serverstatus.app.repository.info.LineChartRepository;
import run.serverstatus.app.service.LineChartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class LineChartServiceImpl implements LineChartService {

    private final LineChartRepository lineChartRepository;

    public LineChartServiceImpl(LineChartRepository lineChartRepository) {
        this.lineChartRepository = lineChartRepository;
    }

    /**
     * Find CpuTemperature Info by repository
     *
     * @return list
     */
    @Override
    public Map<String, List<double[]>> findAllArrayMin(int limit) {
        List<LineChartInfo> lineChartInfo = lineChartRepository.findRecentLineChartInfoMinute(limit);
        List<double[]> cpuLoadArray = new LinkedList<>();
        List<double[]> cpuTempArray = new LinkedList<>();
        List<double[]> netWorkSpeedInArray = new LinkedList<>();
        List<double[]> netWorkSpeedOutArray = new LinkedList<>();
        HashMap<String, List<double[]>> map = new HashMap<>();

        for (LineChartInfo lcInfo : lineChartInfo) {
            double[] in = new double[2];
            double[] out = new double[2];
            double longTime = lcInfo.getLongTime() + 1000 * 60 * 60 * 8L;//+ 8 hours -> beijing time

            in[0] = longTime;
            in[1] = lcInfo.getInternetSpeedIn();
            netWorkSpeedInArray.add(in);

            out[0] = longTime;
            out[1] = lcInfo.getInternetSpeedOut();
            netWorkSpeedOutArray.add(out);
        }
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
    public List<double[]> findCPULoadArrayMin(int limit) {
        return null;
    }

/*    @Override
    public List<List<Double>> findRecentCPUTempMinute() {
        List<LineChartInfo> recentCPUTempMinute = lineChartRepository.findRecentLineChartInfoMinute(30);
        List<List<Double>> cpuTemp = new LinkedList<>();
        for (LineChartInfo lineChartInfo : recentCPUTempMinute) {
            try {
                double cpuTemperature = lineChartInfo.getCpuTemperature();
                double longTime = lineChartInfo.getLongTime() + 1000 * 60 * 60 * 8L;*//*+ 8 hours -> beijing time*//*
                cpuTemp.add(Arrays.asList(longTime, cpuTemperature));
            } catch (NumberFormatException e) {
                log.info("LineChartServiceImpl.findRecentCPUTempMinute -> NumberFormatException......");
            }
        }
        return cpuTemp;
    }*/
}