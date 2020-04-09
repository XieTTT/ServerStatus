package run.serverstatus.app.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties("serverstatus")
public class ServerStatusProperties {
    private String language;
    private String serverName;
    private String processNum;
    private String dateFormat;
    private String mark;
    private Map<String, String> account;
    private Map<String, String> mail;
}
