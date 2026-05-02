import { Component, OnInit, signal } from '@angular/core';
import { DemoDisplay } from '../demo-display/demo-display';
import { DemoResult } from '../demoResult';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-demo',
  imports: [DemoDisplay],
  templateUrl: './demo.html',
  styleUrl: './demo.css',
})
export class Demo implements OnInit {
  demoResults = signal<DemoResult[]>([]);

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.getResults();
  }

  addDemo(value: string) {
    this.addNewResult(value);
    this.demoResults.update(prevResults => [...prevResults, { id: Date.now(), message: value }]);
  }

  private getResults() {
    console.log('Calling for results');
    this.http.get<DemoResult[]>('http://localhost:8888/demo', {observe: 'response'}).subscribe({
      next: (resultingDemos) => {
        console.log('Successful call');
        console.log(resultingDemos.body);
        this.demoResults.update(() => (resultingDemos.body as DemoResult[]));
      },
      error: (err) => {
        console.error(`GET request failed: ${err.message}`);
      },
    });
  }

  private addNewResult(value: string) {
    console.warn(`trying to add new data: ${value}`)
    this.http
      .request('POST', 'http://localhost:8888/demo', {
        body: value,
        responseType: 'text',
      })
      .subscribe({
        next: () => {
          console.log('successfully input data');
        },
        error: (err) => {
          console.error(`POST request failed: ${err.message}`);
        },
      });
  }
}
