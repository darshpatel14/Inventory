package com.inventory.inventorymanagement.export;

import com.inventory.inventorymanagement.entity.Inventory;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@Component("json")
public class JsonExportStrategy implements ExportStrategy {

    @Override
    public void export(List<Inventory> inventoryList,
                       HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        response.setHeader("Content-Disposition",
                "attachment; filename=inventory.json");

        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(response.getOutputStream(), inventoryList);
    }
}