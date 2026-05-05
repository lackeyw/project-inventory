import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoveItem } from './move-item';

describe('MoveItem', () => {
  let component: MoveItem;
  let fixture: ComponentFixture<MoveItem>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MoveItem],
    }).compileComponents();

    fixture = TestBed.createComponent(MoveItem);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
