import { DatabaseRoutingModule } from './database-routing.module';

describe('DatabaseRoutingModule', () => {
  let databaseRoutingModule: DatabaseRoutingModule;

  beforeEach(() => {
    databaseRoutingModule = new DatabaseRoutingModule();
  });

  it('should create an instance', () => {
    expect(databaseRoutingModule).toBeTruthy();
  });
});
