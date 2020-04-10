package run.serverstatus.app.entities.info;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Data
@Component
public class StaticInfo {
    private String mark;
    private String hostname;
    private String intranetIp;
    private String publicIp;
    private String osName;
    private String appBootTime;
    private String osBootTime;
    private Map<String, Object> computerSystem;
    private List<String[]> diskStores;
    private List<String[]> fileSystem;
    private String operatingSystem;
    private String processor;
}
