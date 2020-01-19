import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationCategoryComponent } from './accommodation-category.component';

describe('AccommodationCategoryComponent', () => {
  let component: AccommodationCategoryComponent;
  let fixture: ComponentFixture<AccommodationCategoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccommodationCategoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccommodationCategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
