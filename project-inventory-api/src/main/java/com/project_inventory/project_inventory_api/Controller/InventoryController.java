package com.project_inventory.project_inventory_api.Controller;

import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project_inventory.project_inventory_api.Model.Inventory;
import com.project_inventory.project_inventory_api.Model.InventoryValues;
import com.project_inventory.project_inventory_api.Service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class InventoryController {

    Logger logger = Logger.getLogger(InventoryController.class.getName());

    private final InventoryService inventoryService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/inventory/{type}")
    public List<Inventory> getInventoryByType(@PathVariable String type) {
        logger.info("Fetching inventory items for type: " + type);
        InventoryValues inventoryType = getInventoryType(type);
        return inventoryService.getInventoryByType(inventoryType);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/inventory/{type}/{id}")
    public Inventory getInventoryItemById(@PathVariable String type, @PathVariable Long id) {
        logger.info("Fetching inventory item with ID: " + id + " for type: " + type);
        InventoryValues inventoryType = getInventoryType(type);
        return inventoryService.getInventoryItemById(inventoryType, id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/inventory/{type}")
    public String addInventoryItem(@PathVariable String type, @RequestBody Inventory inventory) {
        logger.info("Adding inventory item for type: " + type + " with details: " + inventory);
        InventoryValues inventoryType = getInventoryType(type);
        inventoryService.addInventoryItem(inventoryType, inventory);
        return "Inventory item added successfully";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PatchMapping("/inventory/{type}/{id}")
    public String updateInventoryItem(@PathVariable String type, @PathVariable Long id,
            @RequestBody Inventory inventory) {
        logger.info("Updating inventory item with ID: " + id + " for type: " + type + " with details: " + inventory);
        InventoryValues inventoryType = getInventoryType(type);
        inventoryService.updateInventoryItem(inventoryType, id, inventory);
        return "Inventory item updated successfully";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/inventory/{type}/{id}")
    public String deleteInventoryItem(@PathVariable String type, @PathVariable Long id) {
        logger.info("Deleting inventory item with ID: " + id + " for type: " + type);
        InventoryValues inventoryType = getInventoryType(type);
        inventoryService.deleteInventoryItem(inventoryType, id);
        return "Inventory item deleted successfully";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/inventory/addToShoppingList/{type}/{id}")
    public String addToShoppingList(@PathVariable String type, @PathVariable Long id) {
        logger.info("Adding inventory item with ID: " + id + " for type: " + type);
        InventoryValues inventoryType = getInventoryType(type);
        inventoryService.addToShoppingList(inventoryType, id);
        return "Inventory item added to shopping list successfully";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/inventory/findItemsByBestBeforeDate/{date}")
    public List<Inventory> findItemsByBestBeforeDate(@PathVariable String date) {
        logger.info("Finding inventory items by best before date: " + date);
        Date parsedDate = Date.valueOf(date);
        return inventoryService.findItemsByBestBeforeDate(parsedDate);
    }

    private InventoryValues getInventoryType(String type) {
        try {
            return InventoryValues.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.severe("Invalid inventory type: " + type);
            throw new IllegalArgumentException("Invalid inventory type: " + type);
        }
    }
}
