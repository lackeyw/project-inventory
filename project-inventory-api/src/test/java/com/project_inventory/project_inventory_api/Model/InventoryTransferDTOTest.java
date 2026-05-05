package com.project_inventory.project_inventory_api.Model;

import org.junit.jupiter.api.Test;

public class InventoryTransferDTOTest {

    @Test
    public void testInventoryTransferDTOGettersAndSetters() {
        InventoryTransferDTO transferDTO = new InventoryTransferDTO();
        transferDTO.setSourceType("some source");
        transferDTO.setDestinationType("some destination");
        transferDTO.setInventoryId(1);
        ;

        assert (transferDTO.getSourceType().equals("some source"));
        assert (transferDTO.getDestinationType().equals("some destination"));
        assert (transferDTO.getInventoryId() == 1);
    }
}
