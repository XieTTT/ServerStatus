package run.serverstatus.app.config;

import run.serverstatus.app.component.LoginHandlerInterception;
import run.serverstatus.app.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {


    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }
    /*TODO 在jar包运行时这个"addViewControllers"不起作用，idea却可以*/
/*    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/sign-in").setViewName("/sign-in");
        registry.addViewController("/sign-up").setViewName("/sign-up");
        registry.addViewController("/settings").setViewName("/settings");
        registry.addViewController("/schedule").setViewName("/schedule");
    }*/

    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterception()).addPathPatterns("/**")
                .excludePathPatterns("/", "/sign-in", "/sign-up", "/login", "/static/**", "/register", "/webjars/**");

    }

    //加载资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
