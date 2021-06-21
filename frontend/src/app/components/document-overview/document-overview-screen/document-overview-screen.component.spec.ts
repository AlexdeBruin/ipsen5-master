import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentOverviewScreenComponent } from './document-overview-screen.component';

describe('DocumentOverviewScreenComponent', () => {
  let component: DocumentOverviewScreenComponent;
  let fixture: ComponentFixture<DocumentOverviewScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentOverviewScreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentOverviewScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
