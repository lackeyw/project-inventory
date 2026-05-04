package com.project_inventory.project_inventory_api.Service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project_inventory.project_inventory_api.Model.ShoppingList;
import com.project_inventory.project_inventory_api.Repository.ShoppingListRepository;

@ExtendWith(MockitoExtension.class)
public class ShoppingListServiceTest {

    @Mock
    private ShoppingListRepository shoppingListRepository;

    @InjectMocks
    private ShoppingListService shoppingListService;

    @Test
    public void testGetAllItems() {
        ShoppingList shoppingList1 = new ShoppingList();
        shoppingList1.setId(1L);
        shoppingList1.setName("Peanuts");
        shoppingList1.setQuantity(10);

        ShoppingList shoppingList2 = new ShoppingList();
        shoppingList2.setId(2L);
        shoppingList2.setName("Cereal");

        List<ShoppingList> shoppingListList = Arrays.asList(shoppingList1, shoppingList2);
        when(shoppingListRepository.findAll()).thenReturn(shoppingListList);

        List<ShoppingList> result = shoppingListService.getShoppingList();

        assert (result.size() == 2);
        assert (result.get(0).getName().equals("Peanuts"));
        assert (result.get(0).getQuantity() == 10);

        assert (result.get(1).getName().equals("Cereal"));
        assert (result.get(1).getQuantity() == null);
    }

    @Test
    public void testGetItemByIdWhenItemExists() {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setId(1L);
        shoppingList.setName("Peanuts");

        when(shoppingListRepository.findById(1L)).thenReturn(Optional.of(shoppingList));

        ShoppingList result = shoppingListService.getShoppingListItemById(1L);

        assert (result != null);
        assert (result.getName().equals("Peanuts"));
        assert (result.getQuantity() == null);
    }

    @Test
    public void testGetItemByIdWhenItemDoesNotExist() {
        when(shoppingListRepository.findById(1L)).thenReturn(Optional.empty());

        ShoppingList result = shoppingListService.getShoppingListItemById(1L);

        assert (result == null);
    }

    @Test
    public void testAddItem() {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName("Peanuts");
        shoppingList.setQuantity(10);

        shoppingListService.addShoppingListItem(shoppingList);

        verify(shoppingListRepository).save(shoppingList);
    }

    @Test
    public void testDeleteItem() {
        shoppingListService.deleteShoppingListItem(1L);

        verify(shoppingListRepository).deleteById(1L);
    }

    @Test
    public void testUpdateItemWhenItemExists() {
        ShoppingList existingShoppingList = new ShoppingList();
        existingShoppingList.setId(1L);
        existingShoppingList.setName("Peanuts");
        existingShoppingList.setQuantity(10);

        ShoppingList updatedShoppingList = new ShoppingList();
        updatedShoppingList.setName("Peanuts Updated");
        updatedShoppingList.setQuantity(20);

        when(shoppingListRepository.findById(1L)).thenReturn(Optional.of(existingShoppingList));

        shoppingListService.updateShoppingListItem(1L, updatedShoppingList);

        verify(shoppingListRepository).save(existingShoppingList);

        assert (existingShoppingList.getName().equals("Peanuts Updated"));
        assert (existingShoppingList.getQuantity() == 20);
    }

    @Test
    public void testUpdateItemWhenItemDoesNotExist() {
        ShoppingList updatedShoppingList = new ShoppingList();
        updatedShoppingList.setName("Peanuts Updated");
        updatedShoppingList.setQuantity(20);

        when(shoppingListRepository.findById(1L)).thenReturn(Optional.empty());

        shoppingListService.updateShoppingListItem(1L, updatedShoppingList);

        verify(shoppingListRepository).findById(1L);
        verify(shoppingListRepository, never()).save(updatedShoppingList);
    }

}
