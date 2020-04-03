package run.serverstatus.app.config;

import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.software.os.OperatingSystem;


@Configuration
public class SensorsConfiguration {
    private static SystemInfo systemInfo = new SystemInfo();

    @Bean
    public OperatingSystem getOperatingSystem() {
        return systemInfo.getOperatingSystem();
    }

    @Bean
    public HardwareAbstractionLayer getHardware() {
        return systemInfo.getHardware();
    }

    @Bean
    public NetworkIF[] getNetworkIF() {
        return systemInfo.getHardware().getNetworkIFs();
    }

}
