package run.serverstatus.app.utils.infoUtils;

import lombok.extern.slf4j.Slf4j;
import run.serverstatus.app.entities.info.StaticInfo;
import org.springframework.stereotype.Component;
import run.serverstatus.app.entities.properties.AppSettings;
import run.serverstatus.app.utils.infoUtils.baseUtil.InfoUtil;

@Slf4j
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

    /**
     * Collect staticInfo while app boot and  cache by staticInfo
     */
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

    /**
     * Refresh StaticInfo while website visit
     *
     * @return StaticInfo
     */
    public StaticInfo refreshStaticInfo() {
        staticInfo.setMark(appSettings.getMark());
        staticInfo.setFileSystem(infoUtil.fileSystem());
        staticInfo.setOperatingSystem(infoUtil.operatingSystem());
        staticInfo.setDiskStores(infoUtil.diskStores());
        return staticInfo;
    }
}
