package run.serverstatus.app.utils.infoUtils;

import org.springframework.stereotype.Component;
import oshi.util.Util;
import run.serverstatus.app.utils.infoUtils.baseUtil.InfoUtil;

import java.util.Arrays;
import java.util.List;

@Component
public class NetworkSpeedInfoUtil {
    private final InfoUtil infoUtil;


    public NetworkSpeedInfoUtil(InfoUtil infoUtil) {
        this.infoUtil = infoUtil;
    }

    public List<long[]> collectNetworkSpeed() {
        long[] speedIn = new long[2];
        long[] speedOut = new long[2];
        Util.sleep(50);/*TODO */
        long time = System.currentTimeMillis();
        speedIn[0] = time;
        speedOut[0] = time;
        List<Long> list = infoUtil.networkTraffic();
        if (list.get(0) != -1) {
            speedIn[1] = list.get(0);
            speedOut[1] = list.get(1);
            return Arrays.asList(speedIn, speedOut);
        }
        return null;
    }
}
