import { TestBed } from '@angular/core/testing';

import { CodebookService } from './codebook.service';

describe('CodebookService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CodebookService = TestBed.get(CodebookService);
    expect(service).toBeTruthy();
  });
});
