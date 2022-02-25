import { TestBed } from '@angular/core/testing';

import { MonkeyService } from './monkey.service';

describe('MonkeyService', () => {
  let service: MonkeyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MonkeyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
