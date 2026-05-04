package com.project_inventory.project_inventory_api.Model;

import org.junit.jupiter.api.Test;

public class ShoppingListTest {

    @Test
    public void testShoppingListGettersAndSetters() {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setId(1L);
        shoppingList.setName("Frozen Peas");
        shoppingList.setQuantity(10);

        assert (shoppingList.getId() == 1L);
        assert (shoppingList.getName().equals("Frozen Peas"));
        assert (shoppingList.getQuantity() == 10);
    }

}
