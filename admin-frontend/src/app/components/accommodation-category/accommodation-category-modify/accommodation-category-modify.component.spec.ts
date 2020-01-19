import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationCategoryModifyComponent } from './accommodation-category-modify.component';

describe('AccommodationCategoryModifyComponent', () => {
  let component: AccommodationCategoryModifyComponent;
  let fixture: ComponentFixture<AccommodationCategoryModifyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccommodationCategoryModifyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccommodationCategoryModifyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
