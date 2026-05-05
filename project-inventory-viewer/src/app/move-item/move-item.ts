import { Component, input, output } from '@angular/core';

@Component({
  selector: 'app-move-item',
  imports: [],
  templateUrl: './move-item.html',
  styleUrl: './move-item.css',
})
export class MoveItem {
  locationName = input.required<string>();
  moveItemEvent = output<string>();
  moveItem() {
    this.moveItemEvent.emit(this.locationName());
  }
}
