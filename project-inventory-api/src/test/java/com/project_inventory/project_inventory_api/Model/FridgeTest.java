package com.project_inventory.project_inventory_api.Model;

import org.junit.jupiter.api.Test;

public class FridgeTest {

    @Test
    public void testFridgeGettersAndSetters() {
        Fridge fridge = new Fridge();
        fridge.setId(1L);
        fridge.setName("Frozen Peas");
        fridge.setQuantity(10);
        fridge.setDate_added(java.sql.Date.valueOf("2024-01-01"));
        fridge.setExpiration_date(java.sql.Date.valueOf("2024-12-31"));

        assert (fridge.getId() == 1L);
        assert (fridge.getName().equals("Frozen Peas"));
        assert (fridge.getQuantity() == 10);
        assert (fridge.getDate_added().equals(java.sql.Date.valueOf("2024-01-01")));
        assert (fridge.getExpiration_date().equals(java.sql.Date.valueOf("2024-12-31")));
    }
}
