import { Component, Input } from '@angular/core';
import { DemoResult } from '../demo/demoResult';

@Component({
  selector: 'app-demo-display',
  imports: [],
  template: `
    <section class="result">
      <p class="result-text"> {{ demoResult.message }}</p>
    </section>
  `,
  styleUrl: './demo-display.css',
})
export class DemoDisplay {
  @Input() demoResult!:DemoResult;
}
