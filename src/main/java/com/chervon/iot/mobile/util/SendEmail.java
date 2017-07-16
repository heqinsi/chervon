package com.chervon.iot.mobile.util;
import com.sun.mail.util.MailSSLSocketFactory;
import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
public class SendEmail
{
    public void sendAttachmentsMail(String email,String url)throws MessagingException,GeneralSecurityException {
                // 收件人电子邮箱
                String to = email;
                // 发件人电子邮箱
                String from = "309449544@qq.com";
                // 指定发送邮件的主机为 localhost
                String host = "smtp.qq.com";
                Properties properties = new Properties();
                // 获取系统属性

                    MailSSLSocketFactory sf  = new MailSSLSocketFactory();
                    sf.setTrustAllHosts(true);
                    properties.put("mail.smtp.auth", "true");
                    properties.put("mail.port",465);
                    properties.setProperty("mail.smtp.host", host);
                    properties.put("mail.smtp.ssl.enable", "true");
                    properties.put("mail.smtp.ssl.socketFactory", sf);


              /*  properties.setProperty("mail.user", "309449544@qq.com");
                properties.setProperty("mail.password","ejwtnyrllmrfbibc");*/
                // 设置邮件服务器
                // 获取默认session对象
                //Session session = Session.getDefaultInstance(properties);
                Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("309449544@qq.com", "zwiqydrhhvdrcadb"); //发件人邮件用户名、密码
            }
        });
                    // 创建默认的 MimeMessage 对象
                    MimeMessage message = new MimeMessage(session);
                    // Set From: 头部头字段
                    message.setFrom(new InternetAddress(from));
                    // Set To: 头部头字段
                    message.addRecipient(Message.RecipientType.TO,
                            new InternetAddress(to));
                    // Set Subject: 头部头字段
                    message.setSubject("This is the Subject Line!");
                    // 设置消息体
                    message.setText(url);
                    // 发送消息
                    Transport.send(message);
                    System.out.println("Sent message successfully....");
        }
}
