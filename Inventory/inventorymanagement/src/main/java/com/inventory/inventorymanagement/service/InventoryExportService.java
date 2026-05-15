package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.dao.InventoryRepository;
import com.inventory.inventorymanagement.entity.Inventory;
import com.inventory.inventorymanagement.export.ExportFactory;
import com.inventory.inventorymanagement.export.ExportStrategy;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class InventoryExportService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ExportFactory exportFactory;

    @Autowired
    private EmailService emailService;

    public void exportInventory(
            String type,
            String fromDate,
            String toDate,
            HttpServletResponse response
    ) throws IOException {

        LocalDate from = LocalDate.parse(fromDate);

        LocalDate to = LocalDate.parse(toDate);

        List<Inventory> inventoryList =
                inventoryRepository.findByDateRange(
                        from,
                        to
                );

        ExportStrategy strategy =
                exportFactory.getStrategy(type);

        strategy.export(inventoryList, response);
    }



    // EMAIL REPORT
    public void emailInventoryReport(
            String email,
            String type,
            String fromDate,
            String toDate
    ) throws Exception {

        LocalDate from = LocalDate.parse(fromDate);

        LocalDate to = LocalDate.parse(toDate);

        List<Inventory> inventoryList =
                inventoryRepository.findByDateRange(
                        from,
                        to
                );

        // TEMP FILE
        File file;

        if(type.equalsIgnoreCase("csv")){

            file = File.createTempFile(
                    "inventory-report",
                    ".csv"
            );

            FileOutputStream fos = new FileOutputStream(file);

            StringBuilder builder = new StringBuilder();

            builder.append(
                    "Product,Stock In,Stock Out,Current Stock,Last Updated\n"
            );

            for(Inventory inventory : inventoryList){

                builder.append(
                                inventory.getProduct().getProductName()
                        ).append(",")

                        .append(inventory.getStockIn())
                        .append(",")

                        .append(inventory.getStockOut())
                        .append(",")

                        .append(inventory.getCurrentStock())
                        .append(",")

                        .append(inventory.getLastUpdated())
                        .append("\n");
            }

            fos.write(
                    builder.toString().getBytes()
            );

            fos.close();

        } else {

            file = File.createTempFile(
                    "inventory-report",
                    ".pdf"
            );

            Document document = new Document();

            PdfWriter.getInstance(
                    document,
                    new FileOutputStream(file)
            );

            document.open();

            Font font =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD

                    );

            Paragraph title =
                    new Paragraph(
                            "Inventory Report",
                            font
                    );

            title.setAlignment(Element.ALIGN_CENTER);


            document.add(title);

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);

            table.setWidthPercentage(100);

            // HEADER
            table.addCell("Product");
            table.addCell("Stock In");
            table.addCell("Stock Out");
            table.addCell("Current Stock");
            table.addCell("Last Updated");

            // DATA
            for(Inventory inventory : inventoryList){

                table.addCell( inventory.getProduct().getProductName());

                table.addCell(String.valueOf(inventory.getStockIn()));

                table.addCell(String.valueOf(inventory.getStockOut()));

                table.addCell(String.valueOf(inventory.getCurrentStock()));

                table.addCell(String.valueOf(inventory.getLastUpdated()));
            }

            document.add(table);

            document.close();
        }

        emailService.sendEmailWithAttachment(
                email,
                file
        );
    }

}