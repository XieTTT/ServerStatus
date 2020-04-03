package run.serverstatus.app.entities.info;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component("lineChartInfo")
public class LineChartInfo {
    private double cpuLoad;
    private double cpuTemperature;
    private long longTime;
    private long internetSpeedIn;
    private long internetSpeedOut;
    private String osTime;
}
