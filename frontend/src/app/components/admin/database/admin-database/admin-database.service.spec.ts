import { TestBed, inject } from '@angular/core/testing';

import { AdminDatabaseService } from './admin-database.service';

describe('AdminDatabaseService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AdminDatabaseService]
    });
  });

  it('should be created', inject([AdminDatabaseService], (service: AdminDatabaseService) => {
    expect(service).toBeTruthy();
  }));
});
