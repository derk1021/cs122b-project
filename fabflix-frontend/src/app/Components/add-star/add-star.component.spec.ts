import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddStarComponent } from './add-star.component';

describe('AddStarComponent', () => {
  let component: AddStarComponent;
  let fixture: ComponentFixture<AddStarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddStarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddStarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
