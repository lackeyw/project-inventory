export interface Item {
  id: number;
  name: string;
  quantity?: number;
  date_added: Date; //can use string??
  expiration_date?: Date;
}
