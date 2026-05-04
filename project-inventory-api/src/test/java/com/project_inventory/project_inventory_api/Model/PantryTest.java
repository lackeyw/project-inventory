package com.project_inventory.project_inventory_api.Model;

import org.junit.jupiter.api.Test;

public class PantryTest {

    @Test
    public void testPantryGettersAndSetters() {
        Pantry pantry = new Pantry();
        pantry.setId(1L);
        pantry.setName("Frozen Peas");
        pantry.setQuantity(10);
        pantry.setDate_added(java.sql.Date.valueOf("2024-01-01"));
        pantry.setExpiration_date(java.sql.Date.valueOf("2024-12-31"));

        assert (pantry.getId() == 1L);
        assert (pantry.getName().equals("Frozen Peas"));
        assert (pantry.getQuantity() == 10);
        assert (pantry.getDate_added().equals(java.sql.Date.valueOf("2024-01-01")));
        assert (pantry.getExpiration_date().equals(java.sql.Date.valueOf("2024-12-31")));
    }
}
