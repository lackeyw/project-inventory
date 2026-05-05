package com.project_inventory.project_inventory_api.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project_inventory.project_inventory_api.Model.ShoppingList;
import com.project_inventory.project_inventory_api.Repository.ShoppingListRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;

    public List<ShoppingList> getShoppingList() {
        return shoppingListRepository.findAll();
    }

    public ShoppingList getShoppingListItemById(Long id) {
        return shoppingListRepository.findById(id).orElse(null);
    }

    public void addShoppingListItem(ShoppingList shoppingList) {
        List<ShoppingList> existingItems = this.getItemByName(shoppingList.getName());
        if (existingItems.size() > 0) {
            for (ShoppingList existingItem : existingItems) {
                existingItem.setQuantity(existingItem.getQuantity() + shoppingList.getQuantity());
                shoppingListRepository.save(existingItem);
                return;
            }
        } else {
            shoppingListRepository.save(shoppingList);
        }
    }

    public void updateShoppingListItem(Long id, ShoppingList shoppingList) {
        ShoppingList existingItem = shoppingListRepository.findById(id).orElse(null);
        if (existingItem != null) {
            existingItem.setName(shoppingList.getName());
            existingItem.setQuantity(shoppingList.getQuantity());
            shoppingListRepository.save(existingItem);
        }
    }

    public void deleteShoppingListItem(Long id) {
        shoppingListRepository.deleteById(id);
    }

    public List<ShoppingList> getItemByName(String name) {
        return shoppingListRepository.findByName(name);
    }

}
