package com.project_inventory.project_inventory_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project_inventory.project_inventory_api.Model.ShoppingList;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {

}
