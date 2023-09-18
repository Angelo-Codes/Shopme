package com.shopme.admin.user.export;

import com.shopme.admin.user.AbstracExporter;
import com.shopme.common.entity.User;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.util.List;

public class UserExcelExporter extends AbstracExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public UserExcelExporter() {
        workbook = new XSSFWorkbook();
    }

    public void writeHeaderLine() {
        sheet = workbook.createSheet("Users");
        XSSFRow row =  sheet.createRow(0);

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        creatCell(row, 0, "User ID", cellStyle);
        creatCell(row, 1, "E-mail", cellStyle);
        creatCell(row, 2, "First Name", cellStyle);
        creatCell(row, 3, "Last Name", cellStyle);
        creatCell(row, 4, "Roles", cellStyle);
        creatCell(row, 5, "Enable", cellStyle);

    }

    public void creatCell(XSSFRow row, int columnIndex, Object value, CellStyle style) {
        XSSFCell cell =  row.createCell(columnIndex);
        sheet.autoSizeColumn(columnIndex);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }

        cell.setCellValue("User ID");
        cell.setCellStyle(style);
    }

    public void export(List<User> listUser, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/octet-stream", ".xlsx");
        writeHeaderLine();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }

    public void writeDataLines(List<User> listUser) {
        int rowIndex = 1;

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        cellStyle.setFont(font);

        for (User user : listUser) {
            XSSFRow row = sheet.createRow(rowIndex);
            int columnIndex = 0;

            creatCell(row, columnIndex++, user.getId(), cellStyle);
            creatCell(row, columnIndex++, user.getEmail(), cellStyle);
            creatCell(row, columnIndex++, user.getFirstname(), cellStyle);
            creatCell(row, columnIndex++, user.getLastname(), cellStyle);
            creatCell(row, columnIndex++, user.getRoles().toString(), cellStyle);
            creatCell(row, columnIndex++, user.isEnable(), cellStyle);
        }
    }
}
