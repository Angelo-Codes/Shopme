package com.shopme;

import com.shopme.setting.EmailSettingBag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class Utility {
    public static String getSiteUrl(HttpServletRequest request) {
        String siteUrl = request.getRequestURL().toString();
        return siteUrl.replace(request.getServletPath(), "");
    }

    public static JavaMailSenderImpl prepareMailSender(EmailSettingBag settings) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(settings.getHost());
        mailSender.setPort(settings.getPort());
        mailSender.setPassword(settings.getPassword());
        mailSender.setUsername(settings.getUsername());

        Properties mailProperties = new Properties();

        mailProperties.setProperty("mail.smtp.auth", settings.getSmtpAuth());
        mailProperties.setProperty("mail.smtp.starttls.enable", settings.getSmtpSecured());

        mailSender.setJavaMailProperties(mailProperties);

        return mailSender;
    }
}
