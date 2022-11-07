package com.project.inventoryservice.controller;


import com.project.inventoryservice.dto.InventoryResponse;
import com.project.inventoryservice.model.Inventory;
import com.project.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")

public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        return inventoryService.isInStock(skuCode);
    }

    @PostMapping
    public String updateInventory(@RequestBody Inventory inventory){
        inventoryService.updateInventory(inventory);
        return "Inventory Successfully Updated";
    }

}