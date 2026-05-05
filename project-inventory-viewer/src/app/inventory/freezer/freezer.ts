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
  override items = signal<Item[]>([]);
}
