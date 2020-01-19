import { Component, OnInit } from '@angular/core';
import { AccommodationTypeService } from './accommodation-type.service';
import { AccommodationType } from './accommodation-type';
import { TokenStorageService } from '../../auth/token-storage.service';

@Component({
  selector: 'app-accommodation-type',
  templateUrl: './accommodation-type.component.html',
  styleUrls: ['./accommodation-type.component.css']
})
export class AccommodationTypeComponent implements OnInit {

  types: AccommodationType[] = [];
  type = new AccommodationType();
  isAdmin: boolean;

  constructor(private accommodationTypeService: AccommodationTypeService,
    private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    this.accommodationTypeService.fetchTypes().subscribe(
      types => {
        this.types = types;
      }
    )

    if (this.tokenStorage.getAuthorities().includes('ROLE_ADMIN'))
      this.isAdmin = true;
  }

  create() {
    this.accommodationTypeService.create(this.type).subscribe(
      response => { this.types.push(response) }
    );
  }

  delete(id: number) {
    this.accommodationTypeService.remove(id).subscribe(
      response => {
        let index = this.types.findIndex((current) => current.id == response);
        this.types.splice(index, 1);
      }
    );
  }

  applyUpdate(type: AccommodationType) {
    let index = this.types.findIndex((current) => current.id == type.id)
    this.types[index] = type;
  }

}
