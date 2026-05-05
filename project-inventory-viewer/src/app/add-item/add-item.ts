import { Component, input, OnInit, output } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ItemWithLocation } from '../inventory/InventoryItemModel';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-add-item',
  imports: [ReactiveFormsModule],
  templateUrl: './add-item.html',
  styleUrl: './add-item.css',
})
export class AddItem implements OnInit {
  location = input<string>();
  addItemEvent = output<ItemWithLocation>();

  private fb = new FormBuilder();

  addItemForm = this.fb.group({
    name: ['', Validators.required],
    quantity: [null as number | null, Validators.min(1)],
    expiration_date: [null as string | null],
    location: [null as string | null, Validators.required],
  });

  ngOnInit(): void {
    const loc = this.location();
    if (loc) {
      this.addItemForm.controls.location.setValue(loc);
    }
  }

  shouldNotDisplayLocation() {
    return !!this.location();
  }

  isLocationOptionSelected(location: string) {
    return location === this.location();
  }

  onSubmit(): void {
    if (this.addItemForm.invalid) return;

    const { name, quantity, expiration_date, location } = this.addItemForm.getRawValue();

    const item: ItemWithLocation = {
      id: 0,
      name: name!,
      quantity: quantity ?? undefined,
      date_added: new Date(),
      expiration_date: expiration_date ? new Date(expiration_date + 'T00:00:00') : undefined,
      location: location!,
    };

    console.log(`Emitting addItemEvent with item: ${JSON.stringify(item)}`);

    this.addItemEvent.emit(item);
    this.addItemForm.reset();
  }

  isNotDevSituation() {
    return !environment.devRun;
  }
}
