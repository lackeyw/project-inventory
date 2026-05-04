package com.project_inventory.project_inventory_api.Service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project_inventory.project_inventory_api.Model.Pantry;
import com.project_inventory.project_inventory_api.Repository.PantryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PantryService implements LocationService<Pantry> {

    private final PantryRepository pantryRepository;

    @Override
    public List<Pantry> getAllItems() {
        return pantryRepository.findAll();
    }

    @Override
    public Pantry getItemById(Long id) {
        return pantryRepository.findById(id).orElse(null);
    }

    @Override
    public void addItem(Pantry inventory) {
        pantryRepository.save(inventory);
    }

    @Override
    public void updateItem(Long id, Pantry inventory) {
        Pantry existingPantry = pantryRepository.findById(id).orElse(null);
        if (existingPantry != null) {
            existingPantry.setName(inventory.getName());
            existingPantry.setQuantity(inventory.getQuantity());
            existingPantry.setDate_added(inventory.getDate_added());
            existingPantry.setExpiration_date(inventory.getExpiration_date());

            pantryRepository.save(existingPantry);
        }
    }

    @Override
    public void deleteItem(Long id) {
        pantryRepository.deleteById(id);
    }

    @Override
    public List<Pantry> findItemsByBestBeforeDate(Date date) {
        return pantryRepository.findByBestBeforeDate(date);
    }

}
