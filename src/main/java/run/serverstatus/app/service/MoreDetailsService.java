package run.serverstatus.app.service;

import run.serverstatus.app.entities.info.BootInfo;
import run.serverstatus.app.entities.info.LineChartInfo;
import run.serverstatus.app.entities.info.TimedInfo;

public interface MoreDetailsService {
    /**
     * Find Boot Information
     * @return boolean
     */
    BootInfo findBootInfo();

    TimedInfo findTimedInfo();

    LineChartInfo findLineChartInfoMin();
}
