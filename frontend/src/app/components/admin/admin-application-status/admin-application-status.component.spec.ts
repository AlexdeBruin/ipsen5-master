import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminApplicationStatusComponent } from './admin-application-status.component';

describe('AdminApplicationStatusComponent', () => {
  let component: AdminApplicationStatusComponent;
  let fixture: ComponentFixture<AdminApplicationStatusComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminApplicationStatusComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminApplicationStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
