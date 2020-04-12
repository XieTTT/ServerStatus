package run.serverstatus.app.service;

import java.util.List;
import java.util.Map;


public interface LineChartService {
    /**
     * Find CpuTemperature Info by repository
     *
     * @return list
     */
    Map<String, Object> findAllArrayMin(int limit);

    List<double[]> findCPULoadArrayMin(int limit);



}

