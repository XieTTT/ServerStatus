package run.serverstatus.app.entities.info;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Data
@Component("timedInfo")
@Scope("prototype")
public class TimedInfo {
    private int id;
    //基本信息
    private String osTime;
    private String osUptime;
    private String availableMemory;
    private long fanSpeed;
    //进程信息
    private LinkedList<Processor> processorsSortedByCPU = new LinkedList<>();
    private LinkedList<Processor> processorsSortedByMEN = new LinkedList<>();

    public void addProcessorsSortedByCPU(Processor processor) {
        this.processorsSortedByCPU.add(processor);
    }

    public void addProcessorsSortedByMEN(Processor processor) {
        this.processorsSortedByMEN.add(processor);
    }
}

