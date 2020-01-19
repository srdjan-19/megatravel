import { Accommodation } from './../../model/accommodation.model';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { AddressService } from 'src/app/services/address.service';

import { Address } from 'src/app/model/address.model';
declare let L;

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  @Output() getCoordinates = new EventEmitter();

  greenIcon = L.icon({
    iconUrl: 'marker-icon.png',
    iconSize: [32, 45],
    iconAnchor: [22, 44],
    popupAnchor: [-3, -46]
  });

  map: any;
  currentMarker: any;

  constructor() {
  }

  ngOnInit() {
    this.map = L.map('map').setView([45.267136, 19.833549], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(this.map);

    this.currentMarker = L.marker([45.267136, 19.833549], { icon: this.greenIcon }).addTo(this.map);
  }

  setMarker(event) {
    this.map.removeLayer(this.currentMarker);
    this.currentMarker = L.marker(this.map.mouseEventToLatLng(event), { icon: this.greenIcon }).addTo(this.map);
    this.getCoordinates.emit(this.map.mouseEventToLatLng(event));
  }

}
