package com.chervon.iot.common.config;

import com.chervon.iot.mobile.sercuity.filter.HTTPBearerAuthorizeAttribute;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Jonsy
 *
 */
//@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
public class WebSecurityConfig{

    @Bean
    public FilterRegistrationBean jwtFilterRegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        HTTPBearerAuthorizeAttribute httpBearerFilter = new HTTPBearerAuthorizeAttribute();
        registrationBean.setFilter(httpBearerFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/api/v1/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }

//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        HTTPBasicAuthorizeAttribute httpBasicFilter = new HTTPBasicAuthorizeAttribute();
//        registrationBean.setFilter(httpBasicFilter);
//        List<String> urlPatterns = new ArrayList<String>();
//        urlPatterns.add("/api/v1/sessions");
//        registrationBean.setUrlPatterns(urlPatterns);
//        return registrationBean;
//    }
}