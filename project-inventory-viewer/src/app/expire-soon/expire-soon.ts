import { Component, OnInit, signal } from '@angular/core';
import { Item } from '../inventory/InventoryItemModel';

@Component({
  selector: 'app-expire-soon',
  imports: [],
  templateUrl: './expire-soon.html',
  styleUrl: './expire-soon.css',
})
export class ExpireSoon implements OnInit {
  ngOnInit(): void {
    this.retrieveItems();
  }

  expireSoonItems = signal<Item[]>([
    {
      id: 2,
      name: 'Milk',
      date_added: new Date('2026-05-01'),
      expiration_date: new Date('2026-05-15'),
    },
    {
      id: 1,
      name: 'Chicken Breast',
      quantity: 2,
      date_added: new Date('2025-11-08'),
      expiration_date: new Date('2026-05-08'),
    },
  ]);

  private retrieveItems(daysUntilExpired: number = 7): void {
    console.log('retrieving all expiring soon items');
  }
}
