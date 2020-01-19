import { Component, OnInit } from '@angular/core';
import { AccommodationCategory, DeleteAccommodationCategoryRequest, CreateAccommodationCategoryRequest } from './accommodation-category';
import { AccommodationCategoryService } from './accommodation-category.service';
import { TokenStorageService } from '../../auth/token-storage.service';

@Component({
  selector: 'app-accommodation-category',
  templateUrl: './accommodation-category.component.html',
  styleUrls: ['./accommodation-category.component.css']
})
export class AccommodationCategoryComponent implements OnInit {

  categories: AccommodationCategory[] = [];
  category = new AccommodationCategory(0, '');

  isAdmin: boolean;

  constructor(private aCategoryService: AccommodationCategoryService,
    private tokenStorage: TokenStorageService) { }

  ngOnInit() {

    this.aCategoryService.fetchCategories().subscribe(
      categories => {
        this.categories = categories;
      }
    )

    if (this.tokenStorage.getAuthorities().includes('ROLE_ADMIN'))
      this.isAdmin = true;

  }

  delete(id: number) {
    this.aCategoryService.remove(id).subscribe(
      response => {
        let index = this.categories.findIndex((current) => current.id == response);
        this.categories.splice(index, 1);
      }
    );
  }

  create() {
    this.aCategoryService.create(this.category.name).subscribe(
      response => { this.categories.push(response) }
    );
  }

  applyUpdate(category: AccommodationCategory) {
    let index = this.categories.findIndex((current) => current.id == category.id);
    this.categories[index] = category;
  }

}
