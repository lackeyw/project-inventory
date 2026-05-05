import { Component, output } from '@angular/core';

@Component({
  selector: 'app-remove-item',
  imports: [],
  templateUrl: './remove-item.html',
  styleUrl: './remove-item.css',
})
export class RemoveItem {
  removeItemEvent = output<void>();
  removeItem() {
    this.removeItemEvent.emit();
  }
}
