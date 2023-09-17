package com.shopme.admin.user;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shopme.common.entity.User;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class UserPdfExporter extends AbstracExporter{
    public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/pdf", ".pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph paragraph = new Paragraph("List of User");
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);


        document.add(paragraph);
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        paragraph.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table, listUsers);

        document.add(table);

        document.close();

    }
    public void writeTableData(PdfPTable table, List<User> listUser){
        for (User user : listUser) {
            table.addCell(String.valueOf(user.getId()));
            table.addCell(user.getEmail());
            table.addCell(user.getFirstname());
            table.addCell(user.getLastname());
            table.addCell(user.getRoles().toString());
            table.addCell(String.valueOf(user.isEnable()));
        }
    }

    public void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setSize(12);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("User Id", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("First Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Last Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Roles", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Enabled", font));
        table.addCell(cell);
    }

}
