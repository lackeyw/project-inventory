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

import com.project_inventory.project_inventory_api.Model.Freezer;
import com.project_inventory.project_inventory_api.Repository.FreezerRepository;

@ExtendWith(MockitoExtension.class)
public class FreezerServiceTest {

    @Mock
    private FreezerRepository freezerRepository;

    @InjectMocks
    private FreezerService freezerService;

    @Test
    public void testGetAllItems() {
        Freezer freezer1 = new Freezer();
        freezer1.setId(1L);
        freezer1.setName("Frozen Peas");
        freezer1.setQuantity(10);
        freezer1.setDate_added(Date.valueOf("2024-01-01"));
        freezer1.setExpiration_date(Date.valueOf("2024-12-31"));

        Freezer freezer2 = new Freezer();
        freezer2.setId(2L);
        freezer2.setName("Frozen Corn");
        freezer2.setDate_added(Date.valueOf("2026-01-02"));

        List<Freezer> freezerList = Arrays.asList(freezer1, freezer2);
        when(freezerRepository.findAll()).thenReturn(freezerList);

        List<Freezer> result = freezerService.getAllItems();

        assert (result.size() == 2);
        assert (result.get(0).getName().equals("Frozen Peas"));
        assert (result.get(0).getQuantity() == 10);
        assert (result.get(0).getDate_added().equals(Date.valueOf("2024-01-01")));
        assert (result.get(0).getExpiration_date().equals(Date.valueOf("2024-12-31")));

        assert (result.get(1).getName().equals("Frozen Corn"));
        assert (result.get(1).getQuantity() == null);
        assert (result.get(1).getDate_added().equals(Date.valueOf("2026-01-02")));
        assert (result.get(1).getExpiration_date() == null);
    }

    @Test
    public void testGetItemByIdWhenItemExists() {
        Freezer freezer = new Freezer();
        freezer.setId(1L);
        freezer.setName("Frozen Peas");
        freezer.setDate_added(Date.valueOf("2024-01-01"));

        when(freezerRepository.findById(1L)).thenReturn(Optional.of(freezer));

        Freezer result = freezerService.getItemById(1L);

        assert (result != null);
        assert (result.getName().equals("Frozen Peas"));
        assert (result.getDate_added().equals(Date.valueOf("2024-01-01")));
    }

    @Test
    public void testGetItemByIdWhenItemDoesNotExist() {
        when(freezerRepository.findById(1L)).thenReturn(Optional.empty());

        Freezer result = freezerService.getItemById(1L);

        assert (result == null);
    }

    @Test
    public void testAddItem() {
        Freezer freezer = new Freezer();
        freezer.setName("Frozen Peas");
        freezer.setDate_added(Date.valueOf("2024-01-01"));

        freezerService.addItem(freezer);

        verify(freezerRepository).save(freezer);
    }

    @Test
    public void testDeleteItem() {
        freezerService.deleteItem(1L);

        verify(freezerRepository).deleteById(1L);
    }

    @Test
    public void testFindItemsByBestBeforeDate() {
        Freezer freezer1 = new Freezer();
        freezer1.setId(1L);
        freezer1.setName("Frozen Peas");
        freezer1.setDate_added(Date.valueOf("2024-01-01"));
        freezer1.setExpiration_date(Date.valueOf("2024-12-31"));

        Freezer freezer2 = new Freezer();
        freezer2.setId(2L);
        freezer2.setName("Frozen Corn");
        freezer2.setDate_added(Date.valueOf("2026-01-02"));
        freezer2.setExpiration_date(Date.valueOf("2026-12-31"));

        List<Freezer> expectedFreezers = Arrays.asList(freezer1, freezer2);
        when(freezerRepository.findByBestBeforeDate(Date.valueOf("2025-01-01"))).thenReturn(expectedFreezers);

        List<Freezer> result = freezerService.findItemsByBestBeforeDate(Date.valueOf("2025-01-01"));

        assert (result.size() == 2);
        assert (result.get(0).getName().equals("Frozen Peas"));
        assert (result.get(0).getExpiration_date().equals(Date.valueOf("2024-12-31")));

        assert (result.get(1).getName().equals("Frozen Corn"));
        assert (result.get(1).getExpiration_date().equals(Date.valueOf("2026-12-31")));
    }

    @Test
    public void testUpdateItemWhenItemExists() {
        Freezer existingFreezer = new Freezer();
        existingFreezer.setId(1L);
        existingFreezer.setName("Frozen Peas");
        existingFreezer.setQuantity(10);
        existingFreezer.setDate_added(Date.valueOf("2024-01-01"));
        existingFreezer.setExpiration_date(Date.valueOf("2024-12-31"));

        Freezer updatedFreezer = new Freezer();
        updatedFreezer.setName("Frozen Peas Updated");
        updatedFreezer.setQuantity(20);
        updatedFreezer.setDate_added(Date.valueOf("2024-02-01"));
        updatedFreezer.setExpiration_date(Date.valueOf("2025-01-31"));

        when(freezerRepository.findById(1L)).thenReturn(Optional.of(existingFreezer));

        freezerService.updateItem(1L, updatedFreezer);

        verify(freezerRepository).save(existingFreezer);

        assert (existingFreezer.getName().equals("Frozen Peas Updated"));
        assert (existingFreezer.getQuantity() == 20);
        assert (existingFreezer.getDate_added().equals(Date.valueOf("2024-02-01")));
        assert (existingFreezer.getExpiration_date().equals(Date.valueOf("2025-01-31")));
    }

    @Test
    public void testUpdateItemWhenItemDoesNotExist() {
        Freezer updatedFreezer = new Freezer();
        updatedFreezer.setName("Frozen Peas Updated");
        updatedFreezer.setQuantity(20);
        updatedFreezer.setDate_added(Date.valueOf("2024-02-01"));
        updatedFreezer.setExpiration_date(Date.valueOf("2025-01-31"));

        when(freezerRepository.findById(1L)).thenReturn(Optional.empty());

        freezerService.updateItem(1L, updatedFreezer);

        verify(freezerRepository).findById(1L);
        verify(freezerRepository, never()).save(updatedFreezer);
    }

}
