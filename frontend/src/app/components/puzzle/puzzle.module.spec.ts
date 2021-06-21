import { PuzzleModule } from './puzzle.module';

describe('PuzzleModule', () => {
  let puzzleModule: PuzzleModule;

  beforeEach(() => {
    puzzleModule = new PuzzleModule();
  });

  it('should create an instance', () => {
    expect(puzzleModule).toBeTruthy();
  });
});
