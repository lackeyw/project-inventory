package com.project_inventory.project_inventory_api.Model;

import org.junit.jupiter.api.Test;

public class FreezerTest {
    @Test
    public void testFreezerGettersAndSetters() {
        Freezer freezer = new Freezer();
        freezer.setId(1L);
        freezer.setName("Frozen Peas");
        freezer.setQuantity(10);
        freezer.setDate_added(java.sql.Date.valueOf("2024-01-01"));
        freezer.setExpiration_date(java.sql.Date.valueOf("2024-12-31"));

        assert (freezer.getId() == 1L);
        assert (freezer.getName().equals("Frozen Peas"));
        assert (freezer.getQuantity() == 10);
        assert (freezer.getDate_added().equals(java.sql.Date.valueOf("2024-01-01")));
        assert (freezer.getExpiration_date().equals(java.sql.Date.valueOf("2024-12-31")));
    }

}
