package com.shopme.admin.brand;

import com.shopme.common.entity.Brand;
import jakarta.servlet.http.HttpServletResponse;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.util.List;

public class BrandCsvExporter extends AbstracExporter{

    public void export(List<Brand> listBrand, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "text/csv", ".csv", "brands_");
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Brand ID", "Brand Name", "Categories"};
        String[] fieldMapping = {"id", "name", "categories"};

        csvWriter.writeHeader(csvHeader);
        for (Brand brand : listBrand) {
            csvWriter.write(brand, fieldMapping);
        }
        csvWriter.close();
    }
}
