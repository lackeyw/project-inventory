package com.project_inventory.project_inventory_api.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryTransferDTO {
    private Integer inventoryId;
    private String sourceType;
    private String destinationType;
}