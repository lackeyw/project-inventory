import { Component, signal } from '@angular/core';
import { Item } from '../InventoryItemModel';
import { Inventory } from '../inventory';
import { AddItem } from '../../add-item/add-item';
import { RemoveItem } from '../../remove-item/remove-item';
import { MoveItem } from '../../move-item/move-item';

@Component({
  selector: 'app-freezer',
  imports: [AddItem, RemoveItem, MoveItem],
  templateUrl: '../inventory.html',
  styleUrl: '../inventory.css',
})
export class Freezer extends Inventory {
  override className = 'freezer';
  override items = signal<Item[]>([
    {
      id: 1,
      name: 'Chicken Breast',
      quantity: 2,
      date_added: new Date('2025-11-08'),
      expiration_date: new Date('2026-05-08'),
    },
    {
      id: 2,
      name: 'Ice Cream',
      date_added: new Date('2026-05-01'),
    },
  ]);
}
