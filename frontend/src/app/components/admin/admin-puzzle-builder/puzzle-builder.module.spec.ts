import { PuzzleBuildingRoutingModule } from "./puzzle-builder-routing.module";

describe('DatabaseRoutingModule', () => {
  let puzzleRoutingModule: PuzzleBuildingRoutingModule;

  beforeEach(() => {
    puzzleRoutingModule = new PuzzleBuildingRoutingModule();    
  });

  it('should create an instance', () => {
    expect(puzzleRoutingModule).toBeTruthy();
  });
});
