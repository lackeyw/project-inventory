import { Item } from '../inventory/InventoryItemModel';

export function mapRawItemToItem(retrievedItems: Item[]): Item[] {
  return retrievedItems.map((i) => ({
    ...i,
    date_added: new Date(i.date_added),
    expiration_date: i.expiration_date ? new Date(i.expiration_date) : undefined,
  }));
}
