import { CodebookService } from 'src/app/services/codebook.service';
import { AccommodationType } from 'src/app/model/accommodationType.model';
import { AccommodationCategory } from 'src/app/model/accommodationCategory.model';
import { Accommodation, UpdateAccommodationRequest } from './../../../model/accommodation.model';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { AccommodationService } from 'src/app/services/accommodation.service';
import { TokenStorageService } from 'src/app/auth/token-storage.service';

@Component({
  selector: 'app-update-accommodation',
  templateUrl: './update-accommodation.component.html',
  styleUrls: ['./update-accommodation.component.css']
})
export class UpdateAccommodationComponent implements OnInit {

  @Output() updated = new EventEmitter();
  @Input() accommodation: Accommodation;
  name: string;

  types: AccommodationType[];
  categories: AccommodationCategory[];
  selectedCategory: AccommodationCategory;
  selectedType: AccommodationType;

  modified = new UpdateAccommodationRequest();

  constructor(private accommodationService: AccommodationService,
    private codebookService: CodebookService,
    private tokenStorage: TokenStorageService) {
  }

  isLoggedIn: boolean;
  isAgent: boolean;

  ngOnInit() {
    this.name = this.accommodation.name;

    this.modified.id = this.accommodation.id;
    this.modified.name = this.accommodation.name;
    this.modified.capacity = this.accommodation.capacity;
    this.modified.category = this.accommodation.category.name;
    this.modified.type = this.accommodation.type.name;
    this.modified.description = this.accommodation.description;

    if (this.tokenStorage.getToken() != null) {
      this.isLoggedIn = true;

      if (this.tokenStorage.getAuthorities().includes('ROLE_AGENT'))
        this.isAgent = true;
    }


    this.codebookService.fetchCategories().subscribe(
      data => this.categories = data
    )

    this.codebookService.fetchTypes().subscribe(
      data => this.types = data
    )
  }

  confirm() {
    if (this.selectedCategory != undefined)
      this.modified.category = this.selectedCategory.toString();

    if (this.selectedType != undefined)
      this.modified.type = this.selectedType.toString();

    this.accommodationService.update(this.modified).subscribe(
      updated => {
        this.updated.emit(updated);
      },
      error => {
        alert(error.error.message)
      }
    )
  }


}