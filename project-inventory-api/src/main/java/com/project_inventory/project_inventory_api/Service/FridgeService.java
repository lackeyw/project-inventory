package com.project_inventory.project_inventory_api.Service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project_inventory.project_inventory_api.Model.Fridge;
import com.project_inventory.project_inventory_api.Model.Inventory;
import com.project_inventory.project_inventory_api.Repository.FridgeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FridgeService implements LocationService<Fridge> {

    private final FridgeRepository fridgeRepository;

    @Override
    public List<Fridge> getAllItems() {
        return fridgeRepository.findAll();
    }

    @Override
    public Fridge getItemById(Long id) {
        return fridgeRepository.findById(id).orElse(null);
    }

    @Override
    public void addItem(Fridge fridge) {
        List<Fridge> existingItems = this.getItemByName(fridge.getName());
        if (existingItems.size() > 0) {
            for (Fridge existingItem : existingItems) {
                if (existingItem.getDate_added().equals(fridge.getDate_added())) {
                    existingItem.setQuantity(existingItem.getQuantity() + fridge.getQuantity());
                    fridgeRepository.save(existingItem);
                    return;
                }
            }
        } else {
            fridgeRepository.save(fridge);
        }
    }

    @Override
    public void updateItem(Long id, Fridge fridge) {
        Fridge existingFridge = fridgeRepository.findById(id).orElse(null);
        if (existingFridge != null) {
            existingFridge.setName(fridge.getName());
            existingFridge.setQuantity(fridge.getQuantity());
            existingFridge.setDate_added(fridge.getDate_added());
            existingFridge.setExpiration_date(fridge.getExpiration_date());

            fridgeRepository.save(existingFridge);
        }
    }

    @Override
    public void deleteItem(Long id) {
        fridgeRepository.deleteById(id);
    }

    @Override
    public List<Fridge> findItemsByBestBeforeDate(Date date) {
        return fridgeRepository.findByBestBeforeDate(date);
    }

    @Override
    public List<Fridge> getItemByName(String name) {
        return fridgeRepository.findByName(name);
    }

    @Override
    public Fridge toSpecificType(Inventory source) {
        Fridge fridge = new Fridge();
        copyFields(source, fridge);
        return fridge;
    }

}
