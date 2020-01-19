import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { AccommodationType, UpdateAccommodationTypeRequest } from '../accommodation-type';
import { ActivatedRoute, Router } from '@angular/router';
import { AccommodationTypeService } from '../accommodation-type.service';

@Component({
  selector: 'app-accommodation-type-modify',
  templateUrl: './accommodation-type-modify.component.html',
  styleUrls: ['./accommodation-type-modify.component.css']
})
export class AccommodationTypeModifyComponent implements OnInit {

  @Input() type: AccommodationType;
  @Output() updated = new EventEmitter();

  typeName: string;
  update = new UpdateAccommodationTypeRequest();

  constructor(private accommodatonTypeService: AccommodationTypeService) { }

  ngOnInit() {
    this.update.oldName = this.type.name;
    this.update.newName = this.type.name;
    this.typeName = this.type.name;
  }

  apply() {
    this.accommodatonTypeService.update(this.update).subscribe(
      response => {
        console.log(response)
        this.updated.emit(response);
      }
    );
  }

}
