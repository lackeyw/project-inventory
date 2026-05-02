import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DemoDisplay } from './demo-display';

describe('DemoDisplay', () => {
  let component: DemoDisplay;
  let fixture: ComponentFixture<DemoDisplay>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DemoDisplay],
    }).compileComponents();

    fixture = TestBed.createComponent(DemoDisplay);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
