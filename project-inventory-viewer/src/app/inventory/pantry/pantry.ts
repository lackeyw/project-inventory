import { Component, OnInit, signal } from '@angular/core';
import { Item } from '../InventoryItemModel';
import { Inventory } from '../inventory';
import { AddItem } from '../../add-item/add-item';
import { RemoveItem } from '../../remove-item/remove-item';
import { MoveItem } from '../../move-item/move-item';

@Component({
  selector: 'app-pantry',
  imports: [AddItem, RemoveItem, MoveItem],
  templateUrl: '../inventory.html',
  styleUrl: '../inventory.css',
})
export class Pantry extends Inventory {
  override className = 'pantry';

  override items = signal<Item[]>([
    {
      id: 1,
      name: 'Cereal',
      date_added: new Date('2026-05-01'),
    },
    {
      id: 2,
      name: 'Peanut Butter',
      date_added: new Date('2026-05-01'),
    },
    {
      id: 3,
      name: 'Rice',
      date_added: new Date('2026-05-01'),
    },
    {
      id: 4,
      name: 'Banana',
      quantity: 6,
      date_added: new Date('2026-05-01'),
    },
  ]);
}
