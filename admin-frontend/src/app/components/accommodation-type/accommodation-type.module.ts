import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccommodationTypeComponent } from './accommodation-type.component';
import { AccommodationTypeModifyComponent } from './accommodation-type-modify/accommodation-type-modify.component';

@NgModule({
  declarations: [AccommodationTypeComponent, AccommodationTypeModifyComponent],
  imports: [
    CommonModule
  ]
})
export class AccommodationTypeModule { }
