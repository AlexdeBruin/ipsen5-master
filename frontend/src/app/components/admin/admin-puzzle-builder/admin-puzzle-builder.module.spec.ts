import { AdminPuzzleBuilderModule } from './admin-puzzle-builder.module';

describe('AdminPuzzleBuilderModule', () => {
  let adminPuzzleBuilderModule: AdminPuzzleBuilderModule;

  beforeEach(() => {
    adminPuzzleBuilderModule = new AdminPuzzleBuilderModule();
  });

  it('should create an instance', () => {
    expect(adminPuzzleBuilderModule).toBeTruthy();
  });
});
