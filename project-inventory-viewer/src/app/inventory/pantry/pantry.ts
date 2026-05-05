import { Component, signal } from '@angular/core';
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

  override items = signal<Item[]>([]);
}
