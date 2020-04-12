package run.serverstatus.app.entities.info;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
@Data
public class NetworkSpeed {

    private LinkedList<long[]> inH = new LinkedList<>();
    private LinkedList<long[]> outH = new LinkedList<>();
    private LinkedList<long[]> inD = new LinkedList<>();
    private LinkedList<long[]> outD = new LinkedList<>();
    private LinkedList<long[]> inM = new LinkedList<>();
    private LinkedList<long[]> outM = new LinkedList<>();

    public void addInH(long[] in) {
        this.inH.add(in);
    }

    public void addOutH(long[] out) {
        this.outH.add(out  )  ;
    }

    public void addInD(long[] in) {
        this.inD.add(in);
    }

    public void addOutD(long[] out) {
        this.outD.add(out);
    }

    public void addInM(long[] in) {
        this.inM.add(in);
    }

    public void addOutM(long[] out) {
        this.outM.add(out);
    }
}