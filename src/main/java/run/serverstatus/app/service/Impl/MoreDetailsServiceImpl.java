package run.serverstatus.app.service.Impl;

import run.serverstatus.app.entities.info.BootInfo;
import run.serverstatus.app.entities.info.LineChartInfo;
import run.serverstatus.app.entities.info.TimedInfo;
import run.serverstatus.app.repository.info.BootInfoRepository;
import run.serverstatus.app.repository.info.LineChartRepository;
import run.serverstatus.app.repository.info.TimedInfoRepository;
import run.serverstatus.app.service.MoreDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoreDetailsServiceImpl implements MoreDetailsService {

    private final BootInfoRepository bootInfoRep;
    private final TimedInfoRepository timedInfoRep;
    private final LineChartRepository lineChartRep;

    public MoreDetailsServiceImpl(BootInfoRepository bootInfoRep,
                                  TimedInfoRepository timedInfoRep,
                                  LineChartRepository lineChartRep) {
        this.bootInfoRep = bootInfoRep;
        this.timedInfoRep = timedInfoRep;
        this.lineChartRep = lineChartRep;
    }

    /**
     * Find bootInfo by repository  from database
     *
     * @return bootInfo
     */
    @Override
    public BootInfo findBootInfo() {
        return bootInfoRep.findBootInfo();
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
