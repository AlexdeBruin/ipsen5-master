import { TestBed, inject } from '@angular/core/testing';

import { ApplicationStatusService } from './application-status.service';

describe('ApplicationStatusService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ApplicationStatusService]
    });
  });

  it('should be created', inject([ApplicationStatusService], (service: ApplicationStatusService) => {
    expect(service).toBeTruthy();
  }));
});
