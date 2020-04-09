package run.serverstatus.app.utils.infoUtils;

import run.serverstatus.app.entities.info.StaticInfo;
import org.springframework.stereotype.Component;
import run.serverstatus.app.entities.properties.AppSettings;

@Component
public class StaticInfoUtil {
    private final AppSettings appSettings;
    private final InfoUtil infoUtil;
    private final StaticInfo staticInfo;

    public StaticInfoUtil(InfoUtil infoUtil,
                          StaticInfo staticInfo,
                          AppSettings appSettings) {
        this.infoUtil = infoUtil;
        this.staticInfo = staticInfo;
        this.appSettings = appSettings;
    }

    public void collectStaticInfo() {
        staticInfo.setMark(appSettings.getMark());
        staticInfo.setOsName(infoUtil.osName());
        staticInfo.setAppBootTime(infoUtil.osTime());
        staticInfo.setHostname(infoUtil.hostName());
        staticInfo.setIntranetIp(infoUtil.IntranetIp());
        staticInfo.setPublicIp(infoUtil.publicIp());
        staticInfo.setOsBootTime(infoUtil.osBootTime());
        staticInfo.setComputerSystem(infoUtil.computerSystem());
        staticInfo.setDiskStores(infoUtil.diskStores());
        staticInfo.setFileSystem(infoUtil.fileSystem());
        staticInfo.setProcessor(infoUtil.processor());
        staticInfo.setOperatingSystem(infoUtil.operatingSystem());
    }
}
