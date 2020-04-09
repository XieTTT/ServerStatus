package run.serverstatus.app.entities.properties;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
public class AppSettings {
    private String language;
    private String processNum;
    private String mark;
    private String serverName;

}
