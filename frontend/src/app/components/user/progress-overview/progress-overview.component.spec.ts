import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgressOverviewComponent } from './progress-overview.component';

describe('ProgressOverviewComponent', () => {
  let component: ProgressOverviewComponent;
  let fixture: ComponentFixture<ProgressOverviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProgressOverviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProgressOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
