package com.shopme.admin.brand;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AbstracExporter {

    public void setResponseHeader(HttpServletResponse response, String contentType, String extension, String prefix) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timeStap = dateFormat.format(new Date());
        String fileName = prefix + timeStap + extension;

        response.setContentType(contentType);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment, fileName=" + fileName;
        response.setHeader(headerKey, headerValue);

    }
}
