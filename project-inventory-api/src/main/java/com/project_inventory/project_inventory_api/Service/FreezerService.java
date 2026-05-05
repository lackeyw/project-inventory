package com.project_inventory.project_inventory_api.Service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project_inventory.project_inventory_api.Model.Freezer;
import com.project_inventory.project_inventory_api.Model.Inventory;
import com.project_inventory.project_inventory_api.Repository.FreezerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FreezerService implements LocationService<Freezer> {

    private final FreezerRepository freezerRepository;

    @Override
    public List<Freezer> getAllItems() {
        return freezerRepository.findAll();
    }

    @Override
    public Freezer getItemById(Long id) {
        return freezerRepository.findById(id).orElse(null);
    }

    @Override
    public void addItem(Freezer inventory) {
        List<Freezer> existingItems = this.getItemByName(inventory.getName());
        if (existingItems.size() > 0) {
            for (Freezer existingItem : existingItems) {
                if (existingItem.getDate_added().equals(inventory.getDate_added())) {
                    existingItem.setQuantity(existingItem.getQuantity() + inventory.getQuantity());
                    freezerRepository.save(existingItem);
                    return;
                }
            }
        } else {
            freezerRepository.save(inventory);
        }
    }

    @Override
    public void updateItem(Long id, Freezer inventory) {
        Freezer existingFreezer = freezerRepository.findById(id).orElse(null);
        if (existingFreezer != null) {
            existingFreezer.setName(inventory.getName());
            existingFreezer.setQuantity(inventory.getQuantity());
            existingFreezer.setDate_added(inventory.getDate_added());
            existingFreezer.setExpiration_date(inventory.getExpiration_date());

            freezerRepository.save(existingFreezer);
        }
    }

    @Override
    public void deleteItem(Long id) {
        freezerRepository.deleteById(id);
    }

    @Override
    public List<Freezer> findItemsByBestBeforeDate(Date date) {
        return freezerRepository.findByBestBeforeDate(date);
    }

    @Override
    public List<Freezer> getItemByName(String name) {
        return freezerRepository.findByName(name);
    }

    @Override
    public Freezer toSpecificType(Inventory source) {
        Freezer freezer = new Freezer();
        copyFields(source, freezer);
        return freezer;
    }

}
