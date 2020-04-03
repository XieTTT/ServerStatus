package run.serverstatus.app.service;

import run.serverstatus.app.entities.Processor;
import run.serverstatus.app.entities.info.TimedInfo;

import java.util.HashMap;
import java.util.List;

public interface HomeService {
    HashMap<String, Object> findShowInfo();

    List<double[]> findCPULoadArray(int limit);

    List<Processor> findProcessSortedByCPU(int id);

    List<Processor> findProcessSortedByMEN(int id);

    TimedInfo findLastTimedInfo();


}
