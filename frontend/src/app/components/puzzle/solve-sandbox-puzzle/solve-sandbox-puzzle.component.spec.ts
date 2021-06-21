import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SolveSandboxPuzzleComponent } from './solve-sandbox-puzzle.component';

describe('SolveSandboxPuzzleComponent', () => {
  let component: SolveSandboxPuzzleComponent;
  let fixture: ComponentFixture<SolveSandboxPuzzleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SolveSandboxPuzzleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SolveSandboxPuzzleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
