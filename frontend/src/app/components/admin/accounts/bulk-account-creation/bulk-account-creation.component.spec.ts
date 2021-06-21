import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BulkAccountCreationComponent } from './bulk-account-creation.component';

describe('BulkAccountCreationComponent', () => {
  let component: BulkAccountCreationComponent;
  let fixture: ComponentFixture<BulkAccountCreationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BulkAccountCreationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BulkAccountCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
