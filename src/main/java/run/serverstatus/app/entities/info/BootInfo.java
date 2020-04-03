package run.serverstatus.app.entities.info;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class BootInfo {
    private int id;
    private String mark;
    private String hostname;
    private String intranetIp;
    private String publicIp;
    private String osName;
    private String appBootTime;
    private String osBootTime;
    private String osUptime;
}
