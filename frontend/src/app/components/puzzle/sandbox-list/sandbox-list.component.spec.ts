import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SandboxListComponent } from './sandbox-list.component';

describe('SandboxListComponent', () => {
  let component: SandboxListComponent;
  let fixture: ComponentFixture<SandboxListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SandboxListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SandboxListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
