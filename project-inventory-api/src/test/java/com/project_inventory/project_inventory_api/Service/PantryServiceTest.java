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

import com.project_inventory.project_inventory_api.Model.Pantry;
import com.project_inventory.project_inventory_api.Repository.PantryRepository;

@ExtendWith(MockitoExtension.class)
public class PantryServiceTest {

    @Mock
    private PantryRepository pantryRepository;

    @InjectMocks
    private PantryService pantryService;

    @Test
    public void testGetAllItems() {
        Pantry pantry1 = new Pantry();
        pantry1.setId(1L);
        pantry1.setName("Peanuts");
        pantry1.setQuantity(10);
        pantry1.setDate_added(Date.valueOf("2024-01-01"));
        pantry1.setExpiration_date(Date.valueOf("2024-12-31"));

        Pantry pantry2 = new Pantry();
        pantry2.setId(2L);
        pantry2.setName("Cereal");
        pantry2.setDate_added(Date.valueOf("2026-01-02"));

        List<Pantry> pantryList = Arrays.asList(pantry1, pantry2);
        when(pantryRepository.findAll()).thenReturn(pantryList);

        List<Pantry> result = pantryService.getAllItems();

        assert (result.size() == 2);
        assert (result.get(0).getName().equals("Peanuts"));
        assert (result.get(0).getQuantity() == 10);
        assert (result.get(0).getDate_added().equals(Date.valueOf("2024-01-01")));
        assert (result.get(0).getExpiration_date().equals(Date.valueOf("2024-12-31")));

        assert (result.get(1).getName().equals("Cereal"));
        assert (result.get(1).getQuantity() == null);
        assert (result.get(1).getDate_added().equals(Date.valueOf("2026-01-02")));
        assert (result.get(1).getExpiration_date() == null);
    }

    @Test
    public void testGetItemByIdWhenItemExists() {
        Pantry pantry = new Pantry();
        pantry.setId(1L);
        pantry.setName("Peanuts");
        pantry.setDate_added(Date.valueOf("2024-01-01"));

        when(pantryRepository.findById(1L)).thenReturn(Optional.of(pantry));

        Pantry result = pantryService.getItemById(1L);

        assert (result != null);
        assert (result.getName().equals("Peanuts"));
        assert (result.getDate_added().equals(Date.valueOf("2024-01-01")));
    }

    @Test
    public void testGetItemByIdWhenItemDoesNotExist() {
        when(pantryRepository.findById(1L)).thenReturn(Optional.empty());

        Pantry result = pantryService.getItemById(1L);

        assert (result == null);
    }

    @Test
    public void testAddItem() {
        Pantry pantry = new Pantry();
        pantry.setName("Peanuts");
        pantry.setDate_added(Date.valueOf("2024-01-01"));

        pantryService.addItem(pantry);

        verify(pantryRepository).save(pantry);
    }

    @Test
    public void testDeleteItem() {
        pantryService.deleteItem(1L);

        verify(pantryRepository).deleteById(1L);
    }

    @Test
    public void testFindItemsByBestBeforeDate() {
        Pantry pantry1 = new Pantry();
        pantry1.setId(1L);
        pantry1.setName("Peanuts");
        pantry1.setDate_added(Date.valueOf("2024-01-01"));
        pantry1.setExpiration_date(Date.valueOf("2024-12-31"));

        Pantry pantry2 = new Pantry();
        pantry2.setId(2L);
        pantry2.setName("Cereal");
        pantry2.setDate_added(Date.valueOf("2026-01-02"));
        pantry2.setExpiration_date(Date.valueOf("2026-12-31"));

        List<Pantry> expectedPantrys = Arrays.asList(pantry1, pantry2);
        when(pantryRepository.findByBestBeforeDate(Date.valueOf("2025-01-01"))).thenReturn(expectedPantrys);

        List<Pantry> result = pantryService.findItemsByBestBeforeDate(Date.valueOf("2025-01-01"));

        assert (result.size() == 2);
        assert (result.get(0).getName().equals("Peanuts"));
        assert (result.get(0).getExpiration_date().equals(Date.valueOf("2024-12-31")));

        assert (result.get(1).getName().equals("Cereal"));
        assert (result.get(1).getExpiration_date().equals(Date.valueOf("2026-12-31")));
    }

    @Test
    public void testUpdateItemWhenItemExists() {
        Pantry existingPantry = new Pantry();
        existingPantry.setId(1L);
        existingPantry.setName("Peanuts");
        existingPantry.setQuantity(10);
        existingPantry.setDate_added(Date.valueOf("2024-01-01"));
        existingPantry.setExpiration_date(Date.valueOf("2024-12-31"));

        Pantry updatedPantry = new Pantry();
        updatedPantry.setName("Peanuts Updated");
        updatedPantry.setQuantity(20);
        updatedPantry.setDate_added(Date.valueOf("2024-02-01"));
        updatedPantry.setExpiration_date(Date.valueOf("2025-01-31"));

        when(pantryRepository.findById(1L)).thenReturn(Optional.of(existingPantry));

        pantryService.updateItem(1L, updatedPantry);

        verify(pantryRepository).save(existingPantry);

        assert (existingPantry.getName().equals("Peanuts Updated"));
        assert (existingPantry.getQuantity() == 20);
        assert (existingPantry.getDate_added().equals(Date.valueOf("2024-02-01")));
        assert (existingPantry.getExpiration_date().equals(Date.valueOf("2025-01-31")));
    }

    @Test
    public void testUpdateItemWhenItemDoesNotExist() {
        Pantry updatedPantry = new Pantry();
        updatedPantry.setName("Peanuts Updated");
        updatedPantry.setQuantity(20);
        updatedPantry.setDate_added(Date.valueOf("2024-02-01"));
        updatedPantry.setExpiration_date(Date.valueOf("2025-01-31"));

        when(pantryRepository.findById(1L)).thenReturn(Optional.empty());

        pantryService.updateItem(1L, updatedPantry);

        verify(pantryRepository).findById(1L);
        verify(pantryRepository, never()).save(updatedPantry);
    }

}
