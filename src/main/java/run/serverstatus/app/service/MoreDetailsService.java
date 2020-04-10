package run.serverstatus.app.service;

import run.serverstatus.app.entities.info.StaticInfo;
import run.serverstatus.app.entities.info.LineChartInfo;
import run.serverstatus.app.entities.info.TimedInfo;

public interface MoreDetailsService {
    /**
     * Find Boot Information
     * @return boolean
     */
    StaticInfo refreshStaticInfo();

    TimedInfo findTimedInfo();

    LineChartInfo findLineChartInfoMin();
}
