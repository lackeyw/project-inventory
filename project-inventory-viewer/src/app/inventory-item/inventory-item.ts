import { Component, Input } from '@angular/core';
import { Item } from './InventoryItemModel';

@Component({
  selector: 'app-inventory-item',
  imports: [],
  template: `
    <div class="inventory-item">
      <p class="inventory-name">{{ item.name }}</p>
      <p class="inventory-quantity">{{ item.quantity }}</p>
      <p class="inventory-date-added">{{ item.date_added }}</p>
      <p class="inventory-expiration-date">{{ item.expiration_date }}</p>
    </div>
  `,
  styleUrl: './inventory-item.css',
})
export class InventoryItem {
  @Input() item!: Item;
}
