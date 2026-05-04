package com.project_inventory.project_inventory_api.Controller;

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

import com.project_inventory.project_inventory_api.Model.ShoppingList;
import com.project_inventory.project_inventory_api.Service.ShoppingListService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ShoppingListController {

    Logger logger = Logger.getLogger(ShoppingListController.class.getName());

    private final ShoppingListService shoppingListService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/shopping-list")
    public List<ShoppingList> getShoppingList() {
        logger.info("Fetching shopping list");
        return shoppingListService.getShoppingList();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/shopping-list/{id}")
    public ShoppingList getShoppingListItemById(@PathVariable Long id) {
        logger.info("Fetching shopping list item with ID: " + id);
        return shoppingListService.getShoppingListItemById(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/shopping-list")
    public String addShoppingListItem(@RequestBody ShoppingList shoppingList) {
        logger.info("Adding shopping list item with details: " + shoppingList);
        shoppingListService.addShoppingListItem(shoppingList);
        return "Shopping list item added successfully";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PatchMapping("/shopping-list/{id}")
    public String updateShoppingListItem(@PathVariable Long id, @RequestBody ShoppingList shoppingList) {
        logger.info("Updating shopping list item with ID: " + id + " with details: " + shoppingList);
        shoppingListService.updateShoppingListItem(id, shoppingList);
        return "Shopping list item updated successfully";

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/shopping-list/{id}")
    public String deleteShoppingListItem(@PathVariable Long id) {
        logger.info("Deleting shopping list item with ID: " + id);
        shoppingListService.deleteShoppingListItem(id);
        return "Shopping list item deleted successfully";
    }
}
