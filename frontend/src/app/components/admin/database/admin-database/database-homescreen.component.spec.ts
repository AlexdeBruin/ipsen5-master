import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DatabaseHomescreenComponent } from './database-homescreen.component';

describe('DatabaseHomescreenComponent', () => {
  let component: DatabaseHomescreenComponent;
  let fixture: ComponentFixture<DatabaseHomescreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DatabaseHomescreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DatabaseHomescreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
