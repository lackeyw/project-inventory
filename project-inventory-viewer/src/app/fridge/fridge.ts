import { Component, OnInit, signal } from '@angular/core';
import { InventoryItem } from '../inventory-item/inventory-item';
import { HttpClient } from '@angular/common/http';
import { Item } from '../inventory-item/InventoryItemModel';

@Component({
  selector: 'app-fridge',
  imports: [InventoryItem],
  templateUrl: './fridge.html',
  styleUrl: './fridge.css',
})
export class Fridge implements OnInit {
  items = signal<Item[]>([
    {
      id: 1,
      name: 'apple',
      quantity: 3,
      date_added: new Date('2026-01-01'),
    },
    {
      id: 2,
      name: 'Milk',
      date_added: new Date('2026-01-01'),
      expiration_date: new Date(),
    },
  ]);

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.retrieveItems();
  }

  private retrieveItems(): void {
    console.log('Calling for all fridge items');
    // this.http
    //   .get<Item[]>('http://localhost:8888/inventory/fridge', { observe: 'response' })
    //   .subscribe({
    //     next: (retrievedItems) => {
    //       console.log(`Successful call with body: ${retrievedItems.body}`);
    //       this.items.update(() => retrievedItems.body as Item[]);
    //     },
    //     error: (err) => {
    //       console.error(`retrieveItems request failed: ${err.message}`);
    //     },
    //   });
  }

  private retrieveItem(id: number): void {}

  private addItem(item: Item): void {}

  private updateItem(id: number, item: Item): void {}
}
