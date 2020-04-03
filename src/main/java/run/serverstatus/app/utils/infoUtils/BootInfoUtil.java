package run.serverstatus.app.utils.infoUtils;

import run.serverstatus.app.entities.info.BootInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BootInfoUtil {
    @Value("mark")
    private String mark;
    private final InfoUtil infoUtil;
    private final BootInfo bootInfo;

    public BootInfoUtil(InfoUtil infoUtil, BootInfo bootInfo) {
        this.infoUtil = infoUtil;
        this.bootInfo = bootInfo;
    }

    public BootInfo collectBootInfo() {
        bootInfo.setOsName(infoUtil.osName());
        bootInfo.setAppBootTime(infoUtil.osTime());
        bootInfo.setHostname(infoUtil.hostName());
        bootInfo.setIntranetIp(infoUtil.IntranetIp());
        bootInfo.setPublicIp(infoUtil.publicIp());
        bootInfo.setOsBootTime(infoUtil.osBootTime());
        bootInfo.setOsUptime(infoUtil.osUpTime());
        bootInfo.setMark(mark);
        return bootInfo;
    }
}
