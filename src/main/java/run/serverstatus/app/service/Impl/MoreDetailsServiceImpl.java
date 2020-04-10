package run.serverstatus.app.service.Impl;

import run.serverstatus.app.entities.info.StaticInfo;
import run.serverstatus.app.entities.info.LineChartInfo;
import run.serverstatus.app.entities.info.TimedInfo;
import run.serverstatus.app.repository.info.LineChartRepository;
import run.serverstatus.app.repository.info.TimedInfoRepository;
import run.serverstatus.app.service.MoreDetailsService;
import org.springframework.stereotype.Service;
import run.serverstatus.app.utils.infoUtils.StaticInfoUtil;

import java.util.List;

@Service
public class MoreDetailsServiceImpl implements MoreDetailsService {
    private final TimedInfoRepository timedInfoRep;
    private final LineChartRepository lineChartRep;
    private final StaticInfoUtil staticInfoUtil;

    public MoreDetailsServiceImpl(TimedInfoRepository timedInfoRep,
                                  LineChartRepository lineChartRep,
                                  StaticInfoUtil staticInfoUtil) {
        this.timedInfoRep = timedInfoRep;
        this.lineChartRep = lineChartRep;
        this.staticInfoUtil = staticInfoUtil;
    }

    /**
     * Find bootInfo by repository  from database
     *
     * @return bootInfo
     */
    @Override
    public StaticInfo refreshStaticInfo() {
        return staticInfoUtil.refreshStaticInfo();

    }

    /**
     * Find timed info by TimedInfoRepository from database
     *
     * @return TimedInfo
     */

    @Override
    public TimedInfo findTimedInfo() {
        List<TimedInfo> recentTimedInfo = timedInfoRep.findRecentTimedInfo(1);
        return recentTimedInfo.get(0);
    }

    /**
     * Find the last lineChartInfo by LineChartRepository form database
     *
     * @return LineChartInfo
     */
    @Override
    public LineChartInfo findLineChartInfoMin() {
        List<LineChartInfo> recentLineChartInfoMinute = lineChartRep.findRecentLineChartInfoMinute(1);
        return recentLineChartInfoMinute.get(0);
    }
}
