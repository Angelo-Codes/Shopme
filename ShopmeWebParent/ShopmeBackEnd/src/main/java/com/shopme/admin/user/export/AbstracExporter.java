package com.shopme.admin.user.export;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AbstracExporter {

    public void setResponseHeader(HttpServletResponse response, String contenType, String extension) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timeStamp = dateFormat.format(new Date());
        String file = "users_" + timeStamp + extension;

        response.setContentType(contenType);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment: filename=" + file;
        response.setHeader(headerKey, headerValue);

    }
}
