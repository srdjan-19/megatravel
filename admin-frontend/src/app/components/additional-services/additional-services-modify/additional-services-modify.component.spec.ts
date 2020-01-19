import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdditionalServicesModifyComponent } from './additional-services-modify.component';

describe('AdditionalServicesModifyComponent', () => {
  let component: AdditionalServicesModifyComponent;
  let fixture: ComponentFixture<AdditionalServicesModifyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdditionalServicesModifyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdditionalServicesModifyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
