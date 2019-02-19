package cn.xmp.generator.demo.user;

import cn.xmp.generator.demo.user.config.Audience;
import cn.xmp.generator.demo.user.filter.HTTPBasicAuthorizeAttribute;
import cn.xmp.generator.demo.user.filter.HTTPBearerAuthorizeAttribute;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@MapperScan("cn.xmp.generator.demo.user.mapper*")
@SpringBootApplication
@EnableConfigurationProperties(Audience.class)
public class SaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaApplication.class, args);

    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        HTTPBasicAuthorizeAttribute httpBasicFilter = new HTTPBasicAuthorizeAttribute();
        registrationBean.setFilter(httpBasicFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/app/appUser/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean jwtFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        HTTPBearerAuthorizeAttribute httpBearerFilter = new HTTPBearerAuthorizeAttribute();
        registrationBean.setFilter(httpBearerFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/app/appUser/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }

}

