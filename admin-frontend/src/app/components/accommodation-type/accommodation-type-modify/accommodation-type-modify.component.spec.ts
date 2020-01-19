import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationTypeModifyComponent } from './accommodation-type-modify.component';

describe('AccommodationTypeModifyComponent', () => {
  let component: AccommodationTypeModifyComponent;
  let fixture: ComponentFixture<AccommodationTypeModifyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccommodationTypeModifyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccommodationTypeModifyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
