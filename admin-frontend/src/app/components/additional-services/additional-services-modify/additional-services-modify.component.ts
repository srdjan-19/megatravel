import { AdditionalServicesService } from '../additional-services.service';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { AdditionalService } from '../additional-services';
import { UpdateAccommodationTypeRequest } from 'src/app/components/accommodation-type/accommodation-type';

@Component({
  selector: 'app-additional-services-modify',
  templateUrl: './additional-services-modify.component.html',
  styleUrls: ['./additional-services-modify.component.css']
})
export class AdditionalServicesModifyComponent implements OnInit {

  @Input() additionalService: AdditionalService;
  @Output() updated = new EventEmitter();

  additionalServiceName: string;
  update = new UpdateAccommodationTypeRequest();

  constructor(private additionalServicesService: AdditionalServicesService) {

  }

  ngOnInit() {
    this.update.oldName = this.additionalService.name;
    this.update.newName = this.additionalService.name;
    this.additionalServiceName = this.additionalService.name;
  }

  apply() {
    this.additionalServicesService.update(this.update).subscribe(
      response => {
        this.updated.emit(response);
      }
    );
  }

}
