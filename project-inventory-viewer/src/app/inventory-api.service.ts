import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Item } from './inventory/InventoryItemModel';

@Injectable({ providedIn: 'root' })
export class InventoryApiService {
  constructor(private httpClient: HttpClient) {}

  addItem(item: Item, location: string): Observable<string> {
    return this.httpClient.post(`http://localhost:8888/inventory/${location}`, item, {
      responseType: 'text',
    });
  }
}
