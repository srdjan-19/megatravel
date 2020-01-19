import { Component, OnInit } from '@angular/core';
import { AccommodationService } from '../../services/accommodation.service';
import { Accommodation } from '../../model/accommodation.model';
import { TokenStorageService } from '../../auth/token-storage.service';
import { AddressService } from 'src/app/services/address.service';
import { Address } from 'src/app/model/address.model';

@Component({
  selector: 'app-accommodations',
  templateUrl: './accommodations.component.html',
  styleUrls: ['./accommodations.component.css']
})
export class AccommodationsComponent implements OnInit {
  accommodations: Accommodation[];
  addresses: Address[] = [];

  isLoggedIn: boolean;
  isAgent: boolean;

  constructor(private accommodationService: AccommodationService,
    private addressService: AddressService,
    private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    if (this.tokenStorage.getToken() != null) {
      this.isLoggedIn = true;

      if (this.tokenStorage.getAuthorities().includes('ROLE_AGENT'))
        this.isAgent = true;
    }

    this.addressService.fetchAddresses().subscribe(
      data => {
        this.addresses = data;
      })

    this.accommodationService.fetchOwned().subscribe(
      data => { this.accommodations = data }
    )
  }

  applyCreate(accommodation) {
    this.accommodations.push(accommodation)
  }

  applyUpdate(accommodation) {
    let index = this.accommodations.findIndex((current) => current.id == accommodation.id)
    this.accommodations[index] = accommodation;
  }

  delete(accommodation: Accommodation) {
    this.accommodationService.delete(accommodation).subscribe(
      data => {
        let index = this.accommodations.indexOf(accommodation);
        this.accommodations.splice(index, 1);
      },
      err => {
        alert(err.error.message);
      }
    )
  }

}
