import { HttpClient } from '@angular/common/http';
import { Component, OnInit, signal } from '@angular/core';
import { Item } from '../inventory-item/InventoryItemModel';
import { InventoryItem } from '../inventory-item/inventory-item';

@Component({
  selector: 'app-freezer',
  imports: [InventoryItem],
  templateUrl: './freezer.html',
  styleUrl: './freezer.css',
})
export class Freezer implements OnInit {
  items = signal<Item[]>([]);

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.retrieveItems();
  }

  private retrieveItems(): void {
    console.log('Calling for all freezer items');
    this.http
      .get<Item[]>('http://localhost:8888/inventory/freezer', { observe: 'response' })
      .subscribe({
        next: (retrievedItems) => {
          console.log(`Successful call with body: ${retrievedItems.body}`);
          this.items.update(() => retrievedItems.body as Item[]);
        },
        error: (err) => {
          console.error(`retrieveItems request failed: ${err.message}`);
        },
      });
  }

  private retrieveItem(id: number): void {}

  private addItem(item: Item): void {}

  private updateItem(id: number, item: Item): void {}
}
