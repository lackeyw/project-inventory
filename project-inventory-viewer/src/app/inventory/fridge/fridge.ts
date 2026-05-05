import { Component, OnInit, signal } from '@angular/core';
import { Item } from '../InventoryItemModel';
import { Inventory } from '../inventory';
import { AddItem } from '../../add-item/add-item';
import { RemoveItem } from '../../remove-item/remove-item';
import { MoveItem } from '../../move-item/move-item';

@Component({
  selector: 'app-fridge',
  imports: [AddItem, RemoveItem, MoveItem],
  templateUrl: '../inventory.html',
  styleUrl: '../inventory.css',
})
export class Fridge extends Inventory {
  override className = 'fridge';

  override items = signal<Item[]>([
    {
      id: 1,
      name: 'Apple',
      quantity: 3,
      date_added: new Date('2026-05-01'),
    },
    {
      id: 2,
      name: 'Milk',
      date_added: new Date('2026-05-01'),
      expiration_date: new Date('2026-05-15'),
    },
  ]);

  shouldCollapse(field: string) {
    // return this.items().filter((item) => item[field as keyof Item] != undefined).length > 0;
    return false;
  }
}
