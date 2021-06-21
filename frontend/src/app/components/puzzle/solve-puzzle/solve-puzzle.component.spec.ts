import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SolvePuzzleComponent } from './solve-puzzle.component';

describe('SolvePuzzleComponent', () => {
  let component: SolvePuzzleComponent;
  let fixture: ComponentFixture<SolvePuzzleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SolvePuzzleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SolvePuzzleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
