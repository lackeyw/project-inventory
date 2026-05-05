package com.project_inventory.project_inventory_api.Service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project_inventory.project_inventory_api.Model.Freezer;
import com.project_inventory.project_inventory_api.Model.Fridge;
import com.project_inventory.project_inventory_api.Model.Inventory;
import com.project_inventory.project_inventory_api.Model.InventoryValues;
import com.project_inventory.project_inventory_api.Model.Pantry;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @Mock
    private FridgeService fridgeService;

    @Mock
    private FreezerService freezerService;

    @Mock
    private PantryService pantryService;

    @Mock
    private ShoppingListService shoppingListService;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    public void testGetInventoryByTypeForFridge() {
        Fridge fridge = new Fridge();
        fridge.setId(1L);
        fridge.setName("some name");
        fridge.setDate_added(Date.valueOf("2024-01-01"));

        List<Fridge> fridgeList = Arrays.asList(fridge);
        when(fridgeService.getAllItems()).thenReturn(fridgeList);

        List<Inventory> result = inventoryService.getInventoryByType(InventoryValues.FRIDGE);

        assert (result.size() == 1);
        assert (result.get(0).getName().equals("some name"));
        assert (result.get(0).getDate_added().equals(Date.valueOf("2024-01-01")));
    }

    @Test
    public void testGetInventoryByTypeForFreezer() {
        Freezer freezer = new Freezer();
        freezer.setId(1L);
        freezer.setName("some name");
        freezer.setDate_added(Date.valueOf("2024-01-01"));

        List<Freezer> freezerList = Arrays.asList(freezer);
        when(freezerService.getAllItems()).thenReturn(freezerList);

        List<Inventory> result = inventoryService.getInventoryByType(InventoryValues.FREEZER);

        assert (result.size() == 1);
        assert (result.get(0).getName().equals("some name"));
        assert (result.get(0).getDate_added().equals(Date.valueOf("2024-01-01")));
    }

    @Test
    public void testGetInventoryByTypeForPantry() {
        Pantry pantry = new Pantry();
        pantry.setId(1L);
        pantry.setName("some name");
        pantry.setDate_added(Date.valueOf("2024-01-01"));

        List<Pantry> pantryList = Arrays.asList(pantry);
        when(pantryService.getAllItems()).thenReturn(pantryList);

        List<Inventory> result = inventoryService.getInventoryByType(InventoryValues.PANTRY);

        assert (result.size() == 1);
        assert (result.get(0).getName().equals("some name"));
        assert (result.get(0).getDate_added().equals(Date.valueOf("2024-01-01")));
    }

    @Test
    public void testGetInventoryItemById() {
        Pantry pantry = new Pantry();
        pantry.setId(1L);
        pantry.setName("some name");
        pantry.setDate_added(Date.valueOf("2024-01-01"));

        when(pantryService.getItemById(1L)).thenReturn(pantry);

        Inventory result = inventoryService.getInventoryItemById(InventoryValues.PANTRY, 1L);

        assert (result.getName().equals("some name"));
        assert (result.getDate_added().equals(Date.valueOf("2024-01-01")));
    }

    @Test
    public void testGetInventoryItemByIdWhenItemDoesNotExist() {
        when(fridgeService.getItemById(1L)).thenReturn(null);

        Inventory result = inventoryService.getInventoryItemById(InventoryValues.FRIDGE, 1L);

        assert (result == null);

    }

    @Test
    public void testAddInventoryItem() {
        Freezer freezer = new Freezer();
        freezer.setId(1L);
        freezer.setName("some name");
        freezer.setDate_added(Date.valueOf("2024-01-01"));

        inventoryService.addInventoryItem(InventoryValues.FREEZER, freezer);

        verify(freezerService).addItem(freezer);
    }

    @Test
    public void testAddInventoryItemWhenItAlreadyExists() {
        Freezer freezer = new Freezer();
        freezer.setId(1L);
        freezer.setName("some name");
        freezer.setDate_added(Date.valueOf("2024-01-01"));
    }

    @Test
    public void testUpdateInventoryItemWhenExists() {
        Fridge updatedFridge = new Fridge();
        updatedFridge.setName("Apple Updated");
        updatedFridge.setQuantity(20);
        updatedFridge.setDate_added(Date.valueOf("2024-02-01"));
        updatedFridge.setExpiration_date(Date.valueOf("2025-01-31"));

        inventoryService.updateInventoryItem(InventoryValues.FRIDGE, 1L, updatedFridge);

        verify(fridgeService).updateItem(1L, updatedFridge);
    }

    @Test
    public void testDeleteInventoryItem() {
        inventoryService.deleteInventoryItem(InventoryValues.FREEZER, 1L);

        verify(freezerService).deleteItem(1L);
    }
}
