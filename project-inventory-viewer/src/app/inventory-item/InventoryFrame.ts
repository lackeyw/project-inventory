import { Item } from './InventoryItemModel';

export interface InventoryFrame {
  retrieveItems(): void;
  retrieveItem(Id: number): void;
  addItem(item: Item): void;
  updateItem(item: Item): void;
}
