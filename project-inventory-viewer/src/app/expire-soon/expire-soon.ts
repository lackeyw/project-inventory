import { Component, OnInit, signal } from '@angular/core';
import { Item } from '../inventory/InventoryItemModel';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { mapRawItemToItem } from '../common/common-functions';

@Component({
  selector: 'app-expire-soon',
  imports: [],
  templateUrl: './expire-soon.html',
  styleUrl: './expire-soon.css',
})
export class ExpireSoon implements OnInit {
  isDevRun = environment.devRun;

  constructor(private httpClient: HttpClient) {}

  ngOnInit(): void {
    this.retrieveItems();
  }

  expireSoonItems = signal<Item[]>([]);

  retrieveItems(): void {
    console.log(`Calling for retrieving all expiring soon items`);

    if (this.isDevRun) {
      return;
    }

    const todayPlusSevenDays = new Date();
    todayPlusSevenDays.setDate(todayPlusSevenDays.getDate() + 7);
    const dateString = `${todayPlusSevenDays.getFullYear()}-${(todayPlusSevenDays.getMonth() + 1).toString()}-${todayPlusSevenDays.getDate().toString()}`;

    this.httpClient
      .get<
        Item[]
      >(`http://localhost:8888/inventory/findItemsByBestBeforeDate/${dateString}`, { observe: 'response' })
      .subscribe({
        next: (retrievedItems) => {
          console.log(
            `Successful retrievial expired soon items call with body: ${JSON.stringify(retrievedItems.body)}`,
          );
          const mapped = mapRawItemToItem(retrievedItems.body as Item[]);
          this.expireSoonItems.update(() => mapped);
        },
        error: (err) => {
          console.error(`Expiring Soon retrieveItems request failed: ${err.message}`);
        },
      });
  }
}
