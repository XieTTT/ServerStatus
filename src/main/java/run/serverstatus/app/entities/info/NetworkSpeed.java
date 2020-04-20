package run.serverstatus.app.entities.info;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
@SuppressWarnings("all")
public class NetworkSpeed {

    private LinkedList<long[]> inm = new LinkedList<>();
    private LinkedList<long[]> outm = new LinkedList<>();
    private LinkedList<long[]> inH = new LinkedList<>();
    private LinkedList<long[]> outH = new LinkedList<>();
    private LinkedList<long[]> inD = new LinkedList<>();
    private LinkedList<long[]> outD = new LinkedList<>();
    private LinkedList<long[]> inM = new LinkedList<>();
    private LinkedList<long[]> outM = new LinkedList<>();

    public void addInm(long[] in) {
        this.inm.add(in);
    }

    public void addOutm(long[] out) {
        this.outm.add(out);
    }

    public void addInH(long[] in) {
        this.inH.add(in);
    }

    public void addOutH(long[] out) {
        this.outH.add(out);
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

    /*TODO a  bug about '@Data' */
    public LinkedList<long[]> getInm() {
        return inm;
    }

    public void setInm(LinkedList<long[]> inm) {
        this.inm = inm;
    }

    public LinkedList<long[]> getOutm() {
        return outm;
    }

    public void setOutm(LinkedList<long[]> outm) {
        this.outm = outm;
    }

    public LinkedList<long[]> getInH() {
        return inH;
    }

    public void setInH(LinkedList<long[]> inH) {
        this.inH = inH;
    }

    public LinkedList<long[]> getOutH() {
        return outH;
    }

    public void setOutH(LinkedList<long[]> outH) {
        this.outH = outH;
    }

    public LinkedList<long[]> getInD() {
        return inD;
    }

    public void setInD(LinkedList<long[]> inD) {
        this.inD = inD;
    }

    public LinkedList<long[]> getOutD() {
        return outD;
    }

    public void setOutD(LinkedList<long[]> outD) {
        this.outD = outD;
    }

    public LinkedList<long[]> getInM() {
        return inM;
    }

    public void setInM(LinkedList<long[]> inM) {
        this.inM = inM;
    }

    public LinkedList<long[]> getOutM() {
        return outM;
    }

    public void setOutM(LinkedList<long[]> outM) {
        this.outM = outM;
    }
}