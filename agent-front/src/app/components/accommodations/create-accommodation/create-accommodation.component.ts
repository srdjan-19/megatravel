import { LocationService } from './../../../services/location.service';
import { Component, OnInit, ElementRef, Renderer2, Output, Input, EventEmitter } from '@angular/core';
import { CreateAccommodationRequest } from 'src/app/model/accommodation.model';
import { AdditionalService } from 'src/app/model/additionalservice.model';
import { AccommodationType } from 'src/app/model/accommodationType.model';
import { AccommodationCategory } from 'src/app/model/accommodationCategory.model';
import { Address } from 'src/app/model/address.model';
import { Currencies } from 'src/app/model/currencies.enum';
import { Months } from 'src/app/model/months.enum';
import { PriceInSeason, PriceRequest } from 'src/app/model/priceInSeason.model';
import { Cancellation } from 'src/app/model/cancellation.model';
import { AccommodationService } from 'src/app/services/accommodation.service';
import { ImageService } from 'src/app/services/image.service';
import { CodebookService } from 'src/app/services/codebook.service';
import { TokenStorageService } from 'src/app/auth/token-storage.service';

@Component({
  selector: 'app-create-accommodation',
  templateUrl: './create-accommodation.component.html',
  styleUrls: ['./create-accommodation.component.css']
})
export class CreateAccommodationComponent implements OnInit {

  @Output() created = new EventEmitter();
  @Input() addresses: Address[];

  isLoggedIn = false;
  isAgent: boolean;
  markerNotPlaced = true;

  additionalServices: AdditionalService[];
  types: AccommodationType[];
  categories: AccommodationCategory[];
  currencies = Currencies;
  selectedCurrency: Currencies;
  months = Months;
  selectedMonth: Months;

  request: CreateAccommodationRequest = new CreateAccommodationRequest();
  type: AccommodationType = new AccommodationType();
  address: Address = new Address();
  selectedType: number = -1;
  selectedCategory: number = -1;
  selectImagesMessage = "Select images";
  selectedFiles: any[];

  priceInSeason: PriceInSeason = new PriceInSeason();
  errorMessage = "";
  cancellation = new Cancellation();

  keysCurrencies(): Array<string> {
    let keys = Object.keys(this.currencies);
    return keys;
  }
  keysMonths(): Array<string> {
    let keys = Object.keys(this.months);
    return keys;
  }
  constructor(private accommodationService: AccommodationService,
    private locatonService: LocationService,
    private imgService: ImageService,
    private _elementRef: ElementRef,
    private renderer: Renderer2,
    private codebookService: CodebookService,
    private tokenStorage: TokenStorageService) {

  }

  ngOnInit() {
    if (this.tokenStorage.getToken() != null) {
      this.isLoggedIn = true;

      if (this.tokenStorage.getAuthorities().includes('ROLE_AGENT'))
        this.isAgent = true;
    }


    this.codebookService.fetchAdditionalServices().subscribe(
      data => { this.additionalServices = data }
    )

    this.codebookService.fetchCategories().subscribe(
      data => this.categories = data
    )

    this.codebookService.fetchTypes().subscribe(
      data => this.types = data
    )
  }

  setLatLng(coordinates) {
    this.markerNotPlaced = false;
    this.address.latitude = coordinates.lat;
    this.address.longitude = coordinates.lng;

    this.locatonService.findLocation(coordinates.lat, coordinates.lng).subscribe(
      data => {
        let markedAddress = data.results[0].components;
        this.address.street = markedAddress.road + " " + markedAddress.house_number;
        this.address.zip = markedAddress.postcode;
        this.address.country = markedAddress.country;
        this.address.city = markedAddress.city;
      }
    );
  }

  addToPricelist() {
    let price = new PriceRequest();

    let currency = this._elementRef.nativeElement.querySelector('#currency');
    let month = this._elementRef.nativeElement.querySelector('#month');
    let enteredPrice = this._elementRef.nativeElement.querySelector('#price');

    if (this.selectedMonth != undefined)
      price.month = this.selectedMonth;
    else {
      this.renderer.addClass(month, 'border-danger');
      return;
    }

    if (this.selectedCurrency != undefined)
      price.currency = this.selectedCurrency;
    else {
      this.renderer.addClass(currency, 'border-danger');
      return;
    }

    if (this.priceInSeason.price != undefined && this.priceInSeason.price > 0)
      price.price = this.priceInSeason.price;
    else {
      this.renderer.addClass(enteredPrice, 'border-danger');
      return;
    }
    delete this.months[this.selectedMonth]

    this.selectedCurrency = undefined;
    this.selectedMonth = undefined;

    this.request.pricelist.push(price);
  }

  complete() {

    let errors: number = 0;
    let name = this._elementRef.nativeElement.querySelector('#name');
    let category = this._elementRef.nativeElement.querySelector('#category');
    let type = this._elementRef.nativeElement.querySelector('#type');
    let description = this._elementRef.nativeElement.querySelector('#description');
    let cancelingPeriod = this._elementRef.nativeElement.querySelector('#cancelingPeriod');
    let capacity = this._elementRef.nativeElement.querySelector('#capacity');
    let price = this._elementRef.nativeElement.querySelector('#price');

    if (this.request.name == undefined || this.request.name == "") {
      this.renderer.addClass(name, 'border-danger');
      errors++;
    } else {
      this.renderer.removeClass(name, 'border-danger');
    }

    if (this.selectedCategory == -1) {
      this.renderer.addClass(category, 'border-danger');
      errors++;
    } else {
      this.request.category = this.selectedCategory.toString();
      this.renderer.removeClass(category, 'border-danger');
    }

    if (this.selectedType == -1) {
      this.renderer.addClass(type, 'border-danger');
      errors++;
    } else {
      this.request.type = this.selectedType.toString();
      this.renderer.removeClass(type, 'border-danger');
    }

    if (this.request.description === "" || this.request.description === undefined) {
      this.renderer.addClass(description, 'border-danger');
      errors++;
    } else {
      this.renderer.removeClass(description, 'border-danger');
    }

    if (this.selectImagesMessage == "Select images") {
      errors++;
      this.errorMessage = "Select at least one image!";
    }

    if (isNaN(this.request.capacity) || this.request.capacity < 0) {
      this.renderer.addClass(capacity, 'border-danger');
      errors++;
    } else {
      this.renderer.removeClass(capacity, 'border-danger');
    }

    if (isNaN(this.cancellation.daysLeft) || this.cancellation.daysLeft < 0) {
      this.renderer.addClass(cancelingPeriod, 'border-danger');
      errors++;
    } else {
      this.request.cancellation.available = true;
      this.request.cancellation.period = this.cancellation.daysLeft;
      this.renderer.removeClass(cancelingPeriod, 'border-danger');
    }

    this.request.address = new Address();

    if (isNaN(this.priceInSeason.price) || this.priceInSeason.price < 0) {
      this.renderer.addClass(price, 'border-danger');
      errors++;
    } else {
      price = new PriceRequest();
      price.currency = this.selectedCurrency;
      price.month = this.selectedMonth;
      this.request.pricelist.push(price);
    }

    if (errors != 0 && this.errorMessage != "Select at least one image!") {
      this.errorMessage = "Fill required fields corectly!";
    }

    if (errors == 0) {
      for (let service of this.additionalServices) {
        if (service.isChecked) {
          this.request.additionalServices.push(service.name);
        }
      }

      this.request.address = this.address;

      this.accommodationService.create(this.request).subscribe(
        data => {
          this.request = new CreateAccommodationRequest();
          this.selectImagesMessage = "Select images";
          this.address = new Address();
          this.priceInSeason = new PriceInSeason();
          this.selectedCategory = -1;
          this.selectedType = -1;
          this.cancellation = new Cancellation();
          this.months = Months;
          this.selectedMonth = Months.JANUARY;
          this.created.emit(data);
        }, error => {
          alert(error.error.message)
        }
      );

      this.selectedType = -1;
      this.codebookService.fetchAdditionalServices().subscribe(
        data => this.additionalServices = data
      )

      this.codebookService.fetchTypes().subscribe(
        data => this.types = data
      )

    } else {
      return;
    }
  }

  onFilesSelected(event) {
    this.selectedFiles = event.target.files;

    if (this.selectedFiles.length == 0) {
      this.selectImagesMessage = "Select images";
    } else {
      this.selectImagesMessage = "";
      for (let file of this.selectedFiles) {
        this.selectImagesMessage = this.selectImagesMessage + file.name + ", ";
      }
    }
  }

  uploadImages(accId) {
    let fd = new FormData();
    for (let file of this.selectedFiles) {
      fd.append('file', file);
    }
    fd.append('accId', accId);
    this.imgService.uploadImages(fd);
  }

}