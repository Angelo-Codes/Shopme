package com.shopme.setting;

import com.shopme.common.entity.Setting;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


@Component
public class SettingFilter implements Filter {

    @Autowired
    private SettingService service;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String url = servletRequest.getRequestURL().toString();

        if (url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".png") || url.endsWith(".jpg")) {
            chain.doFilter(request, response);
            return;
        }

        List<Setting> generalSetting = service.getGeneralSetting();

        generalSetting.forEach(setting -> {
            System.out.println(setting);
            request.setAttribute(setting.getKey(), setting.getValue());
        });

        chain.doFilter(request, response);
    }
}
