import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpireSoon } from './expire-soon';

describe('ExpireSoon', () => {
  let component: ExpireSoon;
  let fixture: ComponentFixture<ExpireSoon>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExpireSoon],
    }).compileComponents();

    fixture = TestBed.createComponent(ExpireSoon);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
