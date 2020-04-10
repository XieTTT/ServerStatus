package run.serverstatus.app.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;


@Configuration
public class SensorsConfiguration {
    private static OperatingSystem operatingSystem = new SystemInfo().getOperatingSystem();
    private static HardwareAbstractionLayer hardware = new SystemInfo().getHardware();

    @Bean
    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    @Bean
    public HardwareAbstractionLayer getHardware() {
        return hardware;
    }

    @Bean
    public NetworkIF[] getNetworkIF() {
        return hardware.getNetworkIFs();
    }

    @Bean
    public HWDiskStore[] getDiskStores() {
        return hardware.getDiskStores();
    }

    @Bean
    public OSFileStore[] getFileStores() {
        return operatingSystem.getFileSystem().getFileStores();

    }

}
