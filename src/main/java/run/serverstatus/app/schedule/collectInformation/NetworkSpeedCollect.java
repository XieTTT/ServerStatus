package run.serverstatus.app.schedule.collectInformation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import run.serverstatus.app.entities.info.NetworkSpeed;
import run.serverstatus.app.utils.infoUtils.NetworkSpeedInfoUtil;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
@EnableScheduling
public class NetworkSpeedCollect {
    private final NetworkSpeedInfoUtil networkSpeedInfoUtil;
    private final NetworkSpeed networkSpeed;

    private List<long[]> longs;

    public NetworkSpeedCollect(NetworkSpeedInfoUtil networkSpeedInfoUtil,
                               NetworkSpeed networkSpeed) {
        this.networkSpeedInfoUtil = networkSpeedInfoUtil;
        this.networkSpeed = networkSpeed;
        longs = networkSpeedInfoUtil.collectNetworkSpeedPresent();
    }

    /**
     * last 5 min
     */
    @Scheduled(cron = "* * * * * ?")
    public void collect() {
        List<long[]> list = networkSpeedInfoUtil.collectNetworkSpeed();
        networkSpeed.addInm(list.get(0));
        networkSpeed.addOutm(list.get(1));
        if (networkSpeed.getInm().size() > 240) {
            networkSpeed.getInm().pop();
            networkSpeed.getOutm().pop();
        }
    }

    /**
     * last 1 hour
     */
    @Scheduled(cron = "*/12 * * * * ?")
    public void collectH() {
        LinkedList<long[]> list = calculate(12);
        networkSpeed.addInH(list.get(0));
        networkSpeed.addOutH(list.get(1));
        if (networkSpeed.getInH().size() > 240) {
            networkSpeed.getInH().pop();
            networkSpeed.getOutH().pop();
        }
    }

    /**
     * last 24 hour
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void collectD() {
        LinkedList<long[]> list = calculate(300);
        networkSpeed.addInD(list.get(0));
        networkSpeed.addOutD(list.get(1));
        if (networkSpeed.getInD().size() > 240) {
            networkSpeed.getInD().pop();
            networkSpeed.getOutD().pop();
        }
    }

    /**
     * last mouth
     */
    @Scheduled(cron = "0 * */3 * * ?")
    public void collectM() {
        List<long[]> list = networkSpeedInfoUtil.collectNetworkSpeedPresent();
        long[] in = list.get(0);
        long[] out = list.get(1);
        long seconds = (in[0] - longs.get(0)[0]) / 1000;
        in[1] = (in[1] - longs.get(0)[1]) / seconds;
        out[1] = (out[1] - longs.get(1)[1]) / seconds;
        networkSpeed.addInM(in);
        networkSpeed.addInM(out);
        longs = list;
        if (networkSpeed.getInM().size() > 240) {
            networkSpeed.getInM().pop();
            networkSpeed.getOutM().pop();
        }
    }

    private LinkedList<long[]> calculate(int calculateSize) {
        long[] in = new long[2];
        long[] out = new long[2];
        long allIn = 0;
        long allOut = 0;
        LinkedList<long[]> inm = networkSpeed.getInm();
        LinkedList<long[]> outm = networkSpeed.getOutm();
        int size = networkSpeed.getInm().size();
        if (size >= calculateSize) {
            for (int i = size - calculateSize; i < size; i++) {
                allIn = allIn + inm.get(i)[1];
                allOut = allOut + outm.get(i)[1];
            }
            in[1] = allIn / calculateSize;
            out[1] = allOut / calculateSize;
        } else {
            for (int i = 0; i < size; i++) {
                allIn = allIn + inm.get(i)[1];
                allOut = allOut + outm.get(i)[1];
            }
            in[1] = allIn / size;
            out[1] = allOut / size;
        }
        long time = System.currentTimeMillis() + 1000 * 60 * 60 * 8;
        in[0] = time;
        out[0] = time;
        LinkedList<long[]> list = new LinkedList<>();
        list.add(in);
        list.add(out);
        return list;
    }
}
