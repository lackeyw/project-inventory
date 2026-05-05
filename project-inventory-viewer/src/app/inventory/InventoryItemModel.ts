import { InventoryType } from './inventory.enum';

export interface Item {
  id: number;
  name: string;
  quantity?: number;
  date_added: Date; //can use string??
  expiration_date?: Date;
}

export interface ItemWithLocation extends Item {
  location: string;
}
