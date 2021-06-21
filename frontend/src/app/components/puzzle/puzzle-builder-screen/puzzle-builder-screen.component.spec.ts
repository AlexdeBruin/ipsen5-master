import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PuzzleBuilderScreenComponent } from './puzzle-builder-screen.component';

describe('PuzzleBuilderScreenComponent', () => {
  let component: PuzzleBuilderScreenComponent;
  let fixture: ComponentFixture<PuzzleBuilderScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PuzzleBuilderScreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PuzzleBuilderScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
