package com.inventory.inventorymanagement.export;

import com.inventory.inventorymanagement.entity.Inventory;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface ExportStrategy {

    void export(List<Inventory> inventoryList, HttpServletResponse response) throws IOException;


}
