package com.project_inventory.project_inventory_api.Service;

import java.sql.Date;
import java.util.List;

import com.project_inventory.project_inventory_api.Model.Inventory;

public interface LocationService<T extends Inventory> {

    List<T> getAllItems();

    T getItemById(Long id);

    List<T> getItemByName(String name);

    void addItem(T inventory);

    void updateItem(Long id, T inventory);

    void deleteItem(Long id);

    List<T> findItemsByBestBeforeDate(Date date);
}
