import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { AccommodationCategory, UpdateAccommodationCategoryRequest } from '../accommodation-category';
import { AccommodationCategoryService } from '../accommodation-category.service';

@Component({
  selector: 'app-accommodation-category-modify',
  templateUrl: './accommodation-category-modify.component.html',
  styleUrls: ['./accommodation-category-modify.component.css']
})
export class AccommodationCategoryModifyComponent implements OnInit {

  @Input() category: AccommodationCategory;
  @Output() updated = new EventEmitter();

  update = new UpdateAccommodationCategoryRequest();
  categoryName: string;

  constructor(private accommodationCategoryService: AccommodationCategoryService) { }

  ngOnInit() {
    this.update.newName = this.category.name;
    this.update.oldName = this.category.name;
    this.categoryName = this.category.name;
  }

  apply() {
    this.accommodationCategoryService.update(this.update).subscribe(
      response => {
        this.updated.emit(response)
      }
    );
  }

}
