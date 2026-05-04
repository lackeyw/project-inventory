package com.project_inventory.project_inventory_api.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "pantry")
public class Pantry extends Inventory {

    public Pantry() {
    }
}