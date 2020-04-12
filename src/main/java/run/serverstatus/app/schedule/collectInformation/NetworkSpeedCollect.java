package run.serverstatus.app.schedule.collectInformation;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import run.serverstatus.app.entities.info.NetworkSpeed;
import run.serverstatus.app.utils.infoUtils.NetworkSpeedInfoUtil;

import java.util.List;


@Component
@EnableScheduling
public class NetworkSpeedCollect {
    private final NetworkSpeedInfoUtil networkSpeedInfoUtil;
    private final NetworkSpeed networkSpeed;

    public NetworkSpeedCollect(NetworkSpeedInfoUtil networkSpeedInfoUtil,
                               NetworkSpeed networkSpeed) {
        this.networkSpeedInfoUtil = networkSpeedInfoUtil;
        this.networkSpeed = networkSpeed;
    }

    @Scheduled(cron = "* * * * * ?")
    public void collect() {
        List<long[]> list = networkSpeedInfoUtil.collectNetworkSpeed();
        networkSpeed.addInD(list.get(0));
        networkSpeed.addOutD(list.get(1));
        if (networkSpeed.getInD().size() > 3600) {
            networkSpeed.getInD().pop();
        }
    }

}
