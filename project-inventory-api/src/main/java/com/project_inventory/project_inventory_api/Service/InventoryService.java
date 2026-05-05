package com.project_inventory.project_inventory_api.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.project_inventory.project_inventory_api.Model.Inventory;
import com.project_inventory.project_inventory_api.Model.InventoryValues;
import com.project_inventory.project_inventory_api.Model.ShoppingList;

@Service
public class InventoryService {

    private final ShoppingListService shoppingListService;
    private final Map<InventoryValues, LocationService<?>> serviceRegistry;

    public InventoryService(FridgeService fridgeService, FreezerService freezerService,
            PantryService pantryService, ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
        this.serviceRegistry = new HashMap<>();
        this.serviceRegistry.put(InventoryValues.FRIDGE, fridgeService);
        this.serviceRegistry.put(InventoryValues.FREEZER, freezerService);
        this.serviceRegistry.put(InventoryValues.PANTRY, pantryService);
    }

    private <T extends Inventory> LocationService<T> getService(InventoryValues type) {
        LocationService<T> service = (LocationService<T>) serviceRegistry.get(type);
        if (service == null) {
            throw new IllegalArgumentException("Invalid inventory type: " + type);
        }
        return service;
    }

    public List<Inventory> getInventoryByType(InventoryValues type) {
        return new ArrayList<>(getService(type).getAllItems());
    }

    public Inventory getInventoryItemById(InventoryValues type, Long id) {
        return getService(type).getItemById(id);
    }

    public void addInventoryItem(InventoryValues type, Inventory inventory) {
        addViaService(getService(type), inventory);
    }

    public void updateInventoryItem(InventoryValues type, Long id, Inventory inventory) {
        updateViaService(getService(type), id, inventory);
    }

    private <T extends Inventory> void addViaService(LocationService<T> service, Inventory inventory) {
        service.addItem(service.toSpecificType(inventory));
    }

    private <T extends Inventory> void updateViaService(LocationService<T> service, Long id, Inventory inventory) {
        service.updateItem(id, service.toSpecificType(inventory));
    }

    public void deleteInventoryItem(InventoryValues inventoryType, Long id) {
        getService(inventoryType).deleteItem(id);
    }

    public void TransferInventoryItem(InventoryValues fromType, InventoryValues toType, Long id) {
        Inventory item = getInventoryItemById(fromType, id);
        if (item != null) {
            deleteInventoryItem(fromType, id);
            Inventory newItem = new Inventory();
            newItem.setName(item.getName());
            newItem.setDate_added(item.getDate_added());
            newItem.setExpiration_date(item.getExpiration_date());
            newItem.setQuantity(item.getQuantity());
            addInventoryItem(toType, newItem);
        } else {
            throw new IllegalArgumentException("Item with ID " + id + " not found in " + fromType);
        }
    }

    public void TransferInventoryItem(InventoryValues fromType, InventoryValues toType, Inventory inventory) {
        deleteInventoryItem(fromType, inventory.getId());
        addInventoryItem(toType, inventory);
    }

    public void addToShoppingList(InventoryValues inventoryType, Long id) {
        Inventory item = getInventoryItemById(inventoryType, id);
        if (item != null) {
            shoppingListService.addShoppingListItem(convertInventoryToShoppingListItem(item));
        } else {
            throw new IllegalArgumentException("Item with ID " + id + " not found in " + inventoryType);
        }
    }

    public List<Inventory> findItemsByBestBeforeDate(Date date) {
        List<Inventory> result = new ArrayList<>();
        for (LocationService<?> service : serviceRegistry.values()) {
            List<? extends Inventory> items = service.findItemsByBestBeforeDate(date);
            if (items != null && !items.isEmpty()) {
                result.addAll(items);
            }
        }
        return result;
    }

    private ShoppingList convertInventoryToShoppingListItem(Inventory inventory) {
        ShoppingList shoppingListItem = new ShoppingList();
        shoppingListItem.setName(inventory.getName());
        shoppingListItem.setQuantity(inventory.getQuantity());
        return shoppingListItem;
    }

}
