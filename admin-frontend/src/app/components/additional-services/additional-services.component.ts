import { Component, OnInit } from '@angular/core';
import { AdditionalServicesService } from './additional-services.service'
import { AdditionalService } from './additional-services';
import { TokenStorageService } from '../../auth/token-storage.service';

@Component({
  selector: 'app-additional-services',
  templateUrl: './additional-services.component.html',
  styleUrls: ['./additional-services.component.css']
})
export class AdditionalServicesComponent implements OnInit {

  services: AdditionalService[] = [];
  service = new AdditionalService();

  isAdmin: boolean;

  constructor(private additionalServicesService: AdditionalServicesService,
    private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    this.additionalServicesService.fetchAdditionalServices().subscribe(
      services => this.services = services
    );

    if (this.tokenStorage.getAuthorities().includes('ROLE_ADMIN'))
      this.isAdmin = true;

  }

  create() {
    this.additionalServicesService.create(this.service).subscribe(
      response => {
        this.services.push(response)
      }
    );
  }

  delete(id: number) {
    this.additionalServicesService.remove(id).subscribe(
      response => {
        let index = this.services.findIndex((current) => current.id == response)
        this.services.splice(index, 1);
      }
    );
  }

  applyUpdate(additionalService: AdditionalService) {
    let index = this.services.findIndex((current) => current.id == additionalService.id)
    this.services[index] = additionalService;
  }

}
