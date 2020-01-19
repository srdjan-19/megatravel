import { Accommodation } from '../../../model/accommodation.model';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-accommodation-details',
  templateUrl: './accommodation-details.component.html',
  styleUrls: ['./accommodation-details.component.css']
})
export class AccommodationDetailsComponent implements OnInit {

  @Input() accommodation: Accommodation;

  constructor() { }

  ngOnInit() {

  }

}
