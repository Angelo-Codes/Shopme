package com.shopme.admin.user.export;

import com.shopme.admin.user.AbstracExporter;
import com.shopme.common.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.util.List;

public class UserCsvExporter extends AbstracExporter {

    public void export(List<User> listUser, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "text/csv", ".csv");
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"User ID", "E-mail", "First Name", "Last Name", "Roles", "Enabled"};
        String[] fieldMapping = {"id", "email", "firstname", "lastname", "role", "enable"};

        csvWriter.writeHeader(csvHeader);
        for (User user : listUser) {
            csvWriter.write(user, fieldMapping);
        }

        csvWriter.close();
    }
}
