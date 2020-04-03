package run.serverstatus.app.service.Impl;

import run.serverstatus.app.schedule.collectInformation.LineChartInfoCollect;
import run.serverstatus.app.schedule.collectInformation.TimedInfoCollect;
import run.serverstatus.app.service.ScheduleService;
import org.springframework.stereotype.Service;

@Service
public class ScheduledServiceImpl implements ScheduleService {

    private final TimedInfoCollect timedInfoCollect;
    private final LineChartInfoCollect lineChartInfoCollect;

    public ScheduledServiceImpl(TimedInfoCollect timedInfoCollect,
                                LineChartInfoCollect lineChartInfoCollect) {
        this.timedInfoCollect = timedInfoCollect;
        this.lineChartInfoCollect = lineChartInfoCollect;
    }

    /**
     * To set the properties of TimeInfoCollect and the properties of LineChartInfoCollect
     *
     * @param timedEmail  int
     *                    0 -> off
     *                    1 -> Every Half Hour
     *                    2 -> Every Hour
     *                    3 -> Every Half Day (00:00 12:00)
     *                    4 -> Every Day (00:00)
     * @param waringEmail int 0 70 75 80 Celsius
     */
    @Override
    public void scheduleSettings(String timedEmail, String waringEmail) {
        timedInfoCollect.setTimedEmail(Integer.parseInt(timedEmail));
        lineChartInfoCollect.setWaringEmail(Integer.parseInt(waringEmail));
    }
}
