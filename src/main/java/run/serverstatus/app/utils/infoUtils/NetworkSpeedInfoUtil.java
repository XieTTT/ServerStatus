package run.serverstatus.app.utils.infoUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import run.serverstatus.app.utils.infoUtils.baseUtil.InfoUtil;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class NetworkSpeedInfoUtil {
    private final InfoUtil infoUtil;


    public NetworkSpeedInfoUtil(InfoUtil infoUtil) {
        this.infoUtil = infoUtil;
    }

    public List<long[]> collectNetworkSpeed() {
        return getLongs(infoUtil.networkTraffic());
    }

    public List<long[]> collectNetworkSpeedPresent() {
        return getLongs(infoUtil.networkTrafficPresent());
    }

    private List<long[]> getLongs(List<Long> longs) {
        long[] speedIn = new long[2];
        long[] speedOut = new long[2];
        long time = System.currentTimeMillis();
        speedIn[0] = time + 1000 * 60 * 60 * 8;//beijing time
        speedOut[0] = time + 1000 * 60 * 60 * 8;//beijing time
        if (longs.get(0) != -1) {
            speedIn[1] = longs.get(0);
            speedOut[1] = longs.get(1);
            return Arrays.asList(speedIn, speedOut);
        }
        return null;
    }
}
