import { Component } from '@angular/core';
import { ExpireSoon } from '../expire-soon/expire-soon';
import { AddItem } from '../add-item/add-item';
import { ItemWithLocation } from '../inventory/InventoryItemModel';
import { InventoryApiService } from '../inventory-api.service';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-home',
  imports: [ExpireSoon, AddItem],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  isDevRun = environment.devRun;

  constructor(private inventoryApiService: InventoryApiService) {}

  addItem($event: ItemWithLocation) {
    if (this.isDevRun) return;

    this.inventoryApiService.addItem($event, $event.location).subscribe({
      next: () => console.log(`Successfully added item ${$event.name} to ${$event.location}`),
      error: (err) => console.error(`Failed to add item to ${$event.location}: ${err.message}`),
    });
  }
}
