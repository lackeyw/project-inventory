import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit, signal, WritableSignal } from '@angular/core';
import { Item, ItemWithLocation } from './InventoryItemModel';
import { InventoryType } from './inventory.enum';
import { environment } from '../../environments/environment';
import { InventoryApiService } from '../inventory-api.service';

@Injectable()
export abstract class Inventory implements OnInit {
  isDevRun = environment.devRun;
  abstract className: string;
  items: WritableSignal<Item[]> = signal([]);

  constructor(private httpClient: HttpClient, private inventoryApiService: InventoryApiService) {}

  ngOnInit(): void {
    this.retrieveItems();
  }

  retrieveItems(): void {
    console.log(`Calling for retrieving all ${this.className} items`);
    if (this.isDevRun) {
      return;
    }

    this.httpClient
      .get<Item[]>(`http://localhost:8888/inventory/${this.className}`, { observe: 'response' })
      .subscribe({
        next: (retrievedItems) => {
          console.log(
            `Successful retrieving ${this.className} call with body: ${JSON.stringify(retrievedItems.body)}`,
          );
          const mapped = this.mapRawItemToItem(retrievedItems.body as Item[]);
          this.items.update(() => mapped);
        },
        error: (err) => {
          console.error(`${this.className} retrieveItems request failed: ${err.message}`);
        },
      });
  }

  retrieveItem(id: number): void {
    console.log(`Calling for retrieving ${this.className} item with id: ${id}`);
    if (this.isDevRun) {
      return;
    }
    this.httpClient
      .get<Item>(`http://localhost:8888/inventory/${this.className}/${id}`, { observe: 'response' })
      .subscribe({
        next: (retrievedItems) => {
          console.log(
            `Successful retrieve ${this.className} item with id ${id} call with body: ${JSON.stringify(retrievedItems.body)}`,
          );
          const mapped = this.mapRawItemToItem([retrievedItems.body as Item]);
          this.items.update((items) => [...(items || []), ...mapped]);
        },
        error: (err) => {
          console.error(`${this.className} retrieveItem for ${id} request failed: ${err.message}`);
        },
      });
  }

  addItem(itemWithLocation: ItemWithLocation): void {
    var item: Item = {
      id: itemWithLocation.id,
      name: itemWithLocation.name,
      quantity: itemWithLocation.quantity,
      date_added: itemWithLocation.date_added,
      expiration_date: itemWithLocation.expiration_date,
    };

    console.log(
      `Calling to add ${this.className} for item with name: ${item.name}, quantity: ${item.quantity}, date_added: ${item.date_added}, expiration_date: ${item.expiration_date}`,
    );

    if (this.items().some((i) => i.name === item.name)) {
      console.log(`Item with name ${item.name} already exists in ${this.className}`);
      var existingItem = this.items().find((i) => i.name === item.name);
      this.updateItem(existingItem!.id, {
        ...existingItem!,
        quantity: (existingItem!.quantity || 1) + (item.quantity || 1),
      });
      return;
    }

    if (this.isDevRun) {
      this.items.update((items) => [...items, item]);
      return;
    }

    this.inventoryApiService.addItem(item, this.className).subscribe({
      next: () => {
        console.log(`Successfully created item on ${this.className} with name ${item.name}`);
        this.items.update((items) => [...items, item]);
      },
      error: (err) => {
        console.error(
          `${this.className} create item for name ${item.name} request failed: ${err.message}`,
        );
      },
    });
  }

  updateItem(id: number, item: Item): void {
    console.log(
      `Calling to update ${this.className} for item id ${id} with name: ${item.name}, quantity: ${item.quantity}, date_added: ${item.date_added}, expiration_date: ${item.expiration_date}`,
    );

    if (this.isDevRun) {
      this.items.update((items) => items.map((i) => (i.id === id ? item : i)));
      return;
    }

    this.httpClient
      .patch(`http://localhost:8888/inventory/${this.className}/${id}`, item, { responseType: 'text' })
      .subscribe({
        next: () => {
          console.log(`Successfully updated item on ${this.className} with name ${item.name}`);
          this.items.update((items) => items.map((i) => (i.id === id ? item : i)));
        },
        error: (err) => {
          console.error(
            `${this.className} update item for name ${item.name} request failed: ${err.message}`,
          );
        },
      });
  }

  deleteItem(id: number): void {
    console.log(`Calling to delete ${this.className} for item id ${id}`);

    if (this.isDevRun) {
      this.items.update((items) => items.filter((item) => item.id != id));
      return;
    }

    this.httpClient
      .delete(`http://localhost:8888/inventory/${this.className}/${id}`, { responseType: 'text' })
      .subscribe({
        next: () => {
          console.log(`Successfully deleted item for ${this.className} with id ${id}`);
          this.items.update((items) => items.filter((item) => item.id != id));
        },
        error: (err) => {
          console.error(
            `${this.className} delete item for id ${id} request failed: ${err.message}`,
          );
        },
      });
  }

  transferItem(toLocation: string, id: number): void {
    if (toLocation === InventoryType.SHOPPING_LIST) {
      this.sendToShoppingList(id);
    } else {
      console.log(`Calling to transfer ${this.className} item with id ${id} to ${toLocation}`);

      if (this.isDevRun) {
        this.items.update((items) => items.filter((item) => item.id != id));
        return;
      }

      const requestBody = {
        inventoryId: id,
        sourceType: this.className,
        destinationType: toLocation,
      };

      this.httpClient
        .post(`http://localhost:8888/inventory/transfer`, requestBody, { responseType: 'text' })
        .subscribe({
          next: () => {
            console.log(`Successfully transfered item from ${this.className} to ${toLocation}`);
            this.items.update((items) => items.filter((item) => item.id != id));
          },
          error: (err) => {
            console.error(`${this.className} sendToShoppingList request failed: ${err.message}`);
          },
        });
    }
  }

  sendToShoppingList(id: number): void {
    console.log(`Calling to add ${this.className} item with id ${id} to shopping list`);

    if (this.isDevRun) {
      return;
    }

    this.httpClient
      .post(
        `http://localhost:8888/inventory/addToShoppingList/${this.className}/${id}`,
        null,
        { responseType: 'text' },
      )
      .subscribe({
        next: () => {
          console.log(`Successfully added item on ${this.className} to shopping list`);
        },
        error: (err) => {
          console.error(`${this.className} sendToShoppingList request failed: ${err.message}`);
        },
      });
  }

  isExpiringSoon(arg0: Date | undefined): boolean {
    if (arg0) {
      const daysDiff = arg0.getDate() - new Date().getDate();
      return daysDiff <= 7;
    }
    return false;
  }

  getOtherLocationName(arg0: number): string {
    if (arg0 == 0) {
      return InventoryType.SHOPPING_LIST;
    } else {
      const otherLocations = Object.values(InventoryType).filter(
        (value) => value !== this.className && value !== InventoryType.SHOPPING_LIST,
      );
      return otherLocations[arg0 - 1];
    }
  }

  mapRawItemToItem(retrievedItems: Item[]): Item[] {
    return retrievedItems.map((i) => ({
      ...i,
      date_added: new Date(i.date_added),
      expiration_date: i.expiration_date ? new Date(i.expiration_date) : undefined,
    }));
  }
}
