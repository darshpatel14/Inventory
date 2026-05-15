package com.inventory.inventorymanagement.export;

import com.inventory.inventorymanagement.entity.Inventory;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component("pdf")
public class PdfExportStrategy implements ExportStrategy {

    @Override
    public void export(List<Inventory> inventoryList,
                       HttpServletResponse response) throws IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=inventory.pdf");

        try {

            Document document = new Document();

            PdfWriter.getInstance(document, response.getOutputStream());

            document.open();

            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            Paragraph title = new Paragraph("Inventory Report", font);

            title.setAlignment(Element.ALIGN_CENTER);

            document.add(title);

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);

            table.addCell("Product");
            table.addCell("Stock In");
            table.addCell("Stock Out");
            table.addCell("Current Stock");
            table.addCell("Last Updated");

            for (Inventory inv : inventoryList) {

                table.addCell(inv.getProduct().getProductName());
                table.addCell(String.valueOf(inv.getStockIn()));
                table.addCell(String.valueOf(inv.getStockOut()));
                table.addCell(String.valueOf(inv.getCurrentStock()));
                table.addCell(String.valueOf(inv.getLastUpdated()));
            }

            document.add(table);

            document.close();

        } catch (DocumentException e) {
            throw new IOException(e);
        }
    }
}