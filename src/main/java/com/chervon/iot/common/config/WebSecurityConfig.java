package com.chervon.iot.common.config;

import com.chervon.iot.mobile.sercuity.filter.HTTPBearerAuthorizeAttribute;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


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

    /*@Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.qq.com");
        mailSender.setPort(465);

        mailSender.setUsername("309449544@qq.com");
        mailSender.setPassword("ejwtnyrllmrfbibc");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }*/
}