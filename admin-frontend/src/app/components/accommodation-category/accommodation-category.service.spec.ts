import { TestBed } from '@angular/core/testing';

import { AccommodationCategoryService } from './accommodation-category.service';

describe('AccommodationCategoryService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AccommodationCategoryService = TestBed.get(AccommodationCategoryService);
    expect(service).toBeTruthy();
  });
});
