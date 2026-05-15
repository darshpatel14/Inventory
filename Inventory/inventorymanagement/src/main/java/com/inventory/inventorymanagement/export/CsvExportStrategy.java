package com.inventory.inventorymanagement.export;

import com.inventory.inventorymanagement.entity.Inventory;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component("csv")
public class CsvExportStrategy implements ExportStrategy{

    @Override
    public void export(List<Inventory> inventoryList, HttpServletResponse response) throws IOException {


        response.setContentType("text/csv");
        response.setHeader("Content-Disposition","attachment; filename=inventory.csv");

        PrintWriter printWriter = response.getWriter();

        printWriter.println("Product Name, Stock In, Stock Out, Current Stock, Last Updated");

        for(Inventory inventory: inventoryList){
            printWriter.println(
                    inventory.getProduct().getProductName() +","+
                    inventory.getStockIn() +","+
                    inventory.getStockOut()+","+
                    inventory.getCurrentStock()+","+
                    inventory.getLastUpdated()
            );
        }

        printWriter.flush();
        printWriter.close();
    }
}
