package com.project_inventory.project_inventory_api.Service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project_inventory.project_inventory_api.Model.Fridge;
import com.project_inventory.project_inventory_api.Repository.FridgeRepository;

@ExtendWith(MockitoExtension.class)
public class FridgeServiceTest {

    @Mock
    private FridgeRepository fridgeRepository;

    @InjectMocks
    private FridgeService fridgeService;

    @Test
    public void testGetAllItems() {
        Fridge fridge1 = new Fridge();
        fridge1.setId(1L);
        fridge1.setName("Apple");
        fridge1.setQuantity(10);
        fridge1.setDate_added(Date.valueOf("2024-01-01"));
        fridge1.setExpiration_date(Date.valueOf("2024-12-31"));

        Fridge fridge2 = new Fridge();
        fridge2.setId(2L);
        fridge2.setName("Milk");
        fridge2.setDate_added(Date.valueOf("2026-01-02"));

        List<Fridge> fridgeList = Arrays.asList(fridge1, fridge2);
        when(fridgeRepository.findAll()).thenReturn(fridgeList);

        List<Fridge> result = fridgeService.getAllItems();

        assert (result.size() == 2);
        assert (result.get(0).getName().equals("Apple"));
        assert (result.get(0).getQuantity() == 10);
        assert (result.get(0).getDate_added().equals(Date.valueOf("2024-01-01")));
        assert (result.get(0).getExpiration_date().equals(Date.valueOf("2024-12-31")));

        assert (result.get(1).getName().equals("Milk"));
        assert (result.get(1).getQuantity() == null);
        assert (result.get(1).getDate_added().equals(Date.valueOf("2026-01-02")));
        assert (result.get(1).getExpiration_date() == null);
    }

    @Test
    public void testGetItemByIdWhenItemExists() {
        Fridge fridge = new Fridge();
        fridge.setId(1L);
        fridge.setName("Apple");
        fridge.setDate_added(Date.valueOf("2024-01-01"));

        when(fridgeRepository.findById(1L)).thenReturn(Optional.of(fridge));

        Fridge result = fridgeService.getItemById(1L);

        assert (result != null);
        assert (result.getName().equals("Apple"));
        assert (result.getDate_added().equals(Date.valueOf("2024-01-01")));
    }

    @Test
    public void testGetItemByIdWhenItemDoesNotExist() {
        when(fridgeRepository.findById(1L)).thenReturn(Optional.empty());

        Fridge result = fridgeService.getItemById(1L);

        assert (result == null);
    }

    @Test
    public void testAddItem() {
        Fridge fridge = new Fridge();
        fridge.setName("Apple");
        fridge.setDate_added(Date.valueOf("2024-01-01"));

        fridgeService.addItem(fridge);

        verify(fridgeRepository).save(fridge);
    }

    @Test
    public void testDeleteItem() {
        fridgeService.deleteItem(1L);

        verify(fridgeRepository).deleteById(1L);
    }

    @Test
    public void testFindItemsByBestBeforeDate() {
        Fridge fridge1 = new Fridge();
        fridge1.setId(1L);
        fridge1.setName("Apple");
        fridge1.setDate_added(Date.valueOf("2024-01-01"));
        fridge1.setExpiration_date(Date.valueOf("2024-12-31"));

        Fridge fridge2 = new Fridge();
        fridge2.setId(2L);
        fridge2.setName("Milk");
        fridge2.setDate_added(Date.valueOf("2026-01-02"));
        fridge2.setExpiration_date(Date.valueOf("2026-12-31"));

        List<Fridge> expectedFridges = Arrays.asList(fridge1, fridge2);
        when(fridgeRepository.findByBestBeforeDate(Date.valueOf("2025-01-01"))).thenReturn(expectedFridges);

        List<Fridge> result = fridgeService.findItemsByBestBeforeDate(Date.valueOf("2025-01-01"));

        assert (result.size() == 2);
        assert (result.get(0).getName().equals("Apple"));
        assert (result.get(0).getExpiration_date().equals(Date.valueOf("2024-12-31")));

        assert (result.get(1).getName().equals("Milk"));
        assert (result.get(1).getExpiration_date().equals(Date.valueOf("2026-12-31")));
    }

    @Test
    public void testUpdateItemWhenItemExists() {
        Fridge existingFridge = new Fridge();
        existingFridge.setId(1L);
        existingFridge.setName("Apple");
        existingFridge.setQuantity(10);
        existingFridge.setDate_added(Date.valueOf("2024-01-01"));
        existingFridge.setExpiration_date(Date.valueOf("2024-12-31"));

        Fridge updatedFridge = new Fridge();
        updatedFridge.setName("Apple Updated");
        updatedFridge.setQuantity(20);
        updatedFridge.setDate_added(Date.valueOf("2024-02-01"));
        updatedFridge.setExpiration_date(Date.valueOf("2025-01-31"));

        when(fridgeRepository.findById(1L)).thenReturn(Optional.of(existingFridge));

        fridgeService.updateItem(1L, updatedFridge);

        verify(fridgeRepository).save(existingFridge);

        assert (existingFridge.getName().equals("Apple Updated"));
        assert (existingFridge.getQuantity() == 20);
        assert (existingFridge.getDate_added().equals(Date.valueOf("2024-02-01")));
        assert (existingFridge.getExpiration_date().equals(Date.valueOf("2025-01-31")));
    }

    @Test
    public void testUpdateItemWhenItemDoesNotExist() {
        Fridge updatedFridge = new Fridge();
        updatedFridge.setName("Apple Updated");
        updatedFridge.setQuantity(20);
        updatedFridge.setDate_added(Date.valueOf("2024-02-01"));
        updatedFridge.setExpiration_date(Date.valueOf("2025-01-31"));

        when(fridgeRepository.findById(1L)).thenReturn(Optional.empty());

        fridgeService.updateItem(1L, updatedFridge);

        verify(fridgeRepository).findById(1L);
        verify(fridgeRepository, never()).save(updatedFridge);
    }

}
