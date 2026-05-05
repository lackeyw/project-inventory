import { HttpClient } from '@angular/common/http';
import { Item } from './inventory/InventoryItemModel';

export class InventoryAccess {
  public static retrieveAllItems(inventoryType: string, httpClient: HttpClient): Item[] {
    var items: Item[] = [];
    console.log(`Calling for all ${inventoryType} items`);
    httpClient
      .get<Item[]>(`http://localhost:8888/inventory/${inventoryType}`, { observe: 'response' })
      .subscribe({
        next: (retrievedItems) => {
          console.log(`Successful call with body: ${retrievedItems.body}`);
          items = retrievedItems.body as Item[];
        },
        error: (err) => {
          console.error(`retrieveItems request failed: ${err.message}`);
        },
      });

    return items;
  }
}
