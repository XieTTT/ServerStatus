package run.serverstatus.app.service;

public interface ScheduleService {

    /**
     * To set the properties of TimeInfo Collect and the properties of LineChartInfoCollect
     * @param timedEmail int
     *                   0 -> off
     *                   1 -> Every Half Hour
     *                   2 -> Every Hour
     *                   3 -> Every Half Day (00:00 12:00)
     *                   4 -> Every Day (00:00)
     * @param waringEmail int 0 70 75 80 Celsius
     */
    void scheduleSettings(String timedEmail, String waringEmail);
}

