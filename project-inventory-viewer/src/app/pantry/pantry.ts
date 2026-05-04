import { Component, OnInit, signal } from '@angular/core';
import { InventoryItem } from '../inventory-item/inventory-item';
import { Item } from '../inventory-item/InventoryItemModel';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-pantry',
  imports: [InventoryItem],
  templateUrl: './pantry.html',
  styleUrl: './pantry.css',
})
export class Pantry implements OnInit {
  items = signal<Item[]>([]);

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.retrieveItems();
  }

  private retrieveItems(): void {
    console.log('Calling for all pantry items');
    this.http
      .get<Item[]>('http://localhost:8888/inventory/pantry', { observe: 'response' })
      .subscribe({
        next: (retrievedItems) => {
          console.log(`Successful call with body: ${retrievedItems.body}`);
          this.items.update(() => retrievedItems.body as Item[]);
        },
        error: (err) => {
          console.error(`Pantry retrieveItems request failed: ${err.message}`);
        },
      });
  }

  private retrieveItem(id: number): void {}

  private addItem(item: Item): void {}

  private updateItem(id: number, item: Item): void {}
}
