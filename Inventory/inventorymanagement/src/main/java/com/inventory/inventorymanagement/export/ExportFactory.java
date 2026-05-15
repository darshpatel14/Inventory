package com.inventory.inventorymanagement.export;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExportFactory {

    @Autowired
    private Map<String, ExportStrategy> strategies;

    public ExportStrategy getStrategy(String type) {

        ExportStrategy strategy = strategies.get(type.toLowerCase());

        if(strategy == null) {
            throw new RuntimeException("Invalid export type");
        }

        return strategy;
    }
}
