import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit, signal, WritableSignal } from '@angular/core';
import { Item, ItemWithLocation } from './InventoryItemModel';
import { InventoryType } from './inventory.enum';

@Injectable()
export abstract class Inventory implements OnInit {
  abstract className: string;
  items: WritableSignal<Item[]> = signal([]);

  constructor(private httpClient: HttpClient) {}

  ngOnInit(): void {
    this.retrieveItems();
  }

  retrieveItems(): void {
    console.log(`Calling for retrieving all ${this.className} items`);
    // this.httpClient
    //   .get<Item[]>(`http://localhost:8888/inventory/${this.className}`, { observe: 'response' })
    //   .subscribe({
    //     next: (retrievedItems) => {
    //       console.log(
    //         `Successful retrieving ${this.className} call with body: ${retrievedItems.body}`,
    //       );
    //       this.items.update(() => retrievedItems.body as Item[]);
    //     },
    //     error: (err) => {
    //       console.error(`${this.className} retrieveItems request failed: ${err.message}`);
    //     },
    //   });
  }

  retrieveItem(id: number): void {
    console.log(`Calling for retrieving ${this.className} item with id: ${id}`);
    // this.httpClient
    //   .get<Item>(`http://localhost:8888/inventory/${this.className}/${id}`, { observe: 'response' })
    //   .subscribe({
    //     next: (retrievedItems) => {
    //       console.log(
    //         `Successful retrieve ${this.className} item with id ${id} call with body: ${retrievedItems.body}`,
    //       );
    //       this.items.update((items) => [...(items || []), retrievedItems.body as Item]);
    //     },
    //     error: (err) => {
    //       console.error(`${this.className} retrieveItem for ${id} request failed: ${err.message}`);
    //     },
    //   });
  }

  addItem(itemWithLocation: ItemWithLocation): void {
    var item: Item = {
      id: itemWithLocation.id,
      name: itemWithLocation.name,
      quantity: itemWithLocation.quantity,
      date_added: itemWithLocation.date_added,
      expiration_date: itemWithLocation.expiration_date,
    };
    if (this.items().some((i) => i.name === item.name)) {
      console.log(`Item with name ${item.name} already exists in ${this.className}`);
      var existingItem = this.items().find((i) => i.name === item.name);
      console.log(`Found existing item: ${JSON.stringify(existingItem)}`);
      this.updateItem(existingItem!.id, {
        ...existingItem!,
        quantity: (existingItem!.quantity || 1) + (item.quantity || 1),
      });
      return;
    }
    console.log(
      `Calling to add ${this.className} for item with name: ${item.name}, quantity: ${item.quantity}, date_added: ${item.date_added}, expiration_date: ${item.expiration_date}`,
    );
    this.items.update((items) => [...items, item]);
    // this.httpClient
    //   .post<string>(`http://localhost:8888/inventory/${this.className}`, item)
    //   .subscribe({
    //     next: () => {
    //       console.log(`Successfully created item on ${this.className} with name ${item.name}`);
    //     },
    //     error: (err) => {
    //       console.error(
    //         `${this.className} create item for name ${item.name} request failed: ${err.message}`,
    //       );
    //     },
    //   });
  }

  updateItem(id: number, item: Item): void {
    console.log(
      `Calling to update ${this.className} for item id ${id} with name: ${item.name}, quantity: ${item.quantity}, date_added: ${item.date_added}, expiration_date: ${item.expiration_date}`,
    );
    this.items.update((items) => items.map((i) => (i.id === id ? item : i)));
    // this.httpClient
    //   .patch<string>(`http://localhost:8888/inventory/${this.className}/${id}`, item)
    //   .subscribe({
    //     next: () => {
    //       console.log(`Successfully updated item on ${this.className} with name ${item.name}`);
    //     },
    //     error: (err) => {
    //       console.error(
    //         `${this.className} update item for name ${item.name} request failed: ${err.message}`,
    //       );
    //     },
    //   });
  }

  deleteItem(id: number): void {
    console.log(`Calling to delete ${this.className} for item id ${id}`);
    this.items.update((items) => items.filter((item) => item.id != id));
  }

  transferItem(toLocation: string, id: number): void {
    if (toLocation === InventoryType.SHOPPING_LIST) {
      console.log(`Calling to transfer ${this.className} item with id ${id} to shopping list`);
    } else {
      console.log(`Calling to transfer ${this.className} item with id ${id} to ${toLocation}`);
    }
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
}
