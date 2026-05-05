import { Component } from '@angular/core';
import { ExpireSoon } from '../expire-soon/expire-soon';
import { AddItem } from '../add-item/add-item';
import { Item } from '../inventory/InventoryItemModel';

@Component({
  selector: 'app-home',
  imports: [ExpireSoon, AddItem],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  addItem($event: Item) {
    throw new Error('Method not implemented.');
  }
}
