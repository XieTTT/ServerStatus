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

    List<long[]> findNetWorkSpeedIn(String period);

    List<long[]> findNetWorkSpeedOut(String period);


}

