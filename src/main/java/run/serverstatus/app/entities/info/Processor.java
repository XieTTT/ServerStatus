package run.serverstatus.app.entities.info;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Data
@Component("processor")
@Scope("prototype")
public class Processor {
    private String orderBy;
    private int sortID;
    private String PID;
    private String CPU;
    private String MEN;
    private String VSZ;
    private String RSS;
    private String Name;
    private int hourInfo_id;


}
