package run.serverstatus.app.entities.properties;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Scope("prototype")
@Component
public class Account {
    private String username;
    private String password;
}
