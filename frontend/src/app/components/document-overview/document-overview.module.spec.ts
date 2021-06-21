import { DocumentOverviewModule } from './document-overview.module';

describe('DocumentOverviewModule', () => {
  let documentOverviewModule: DocumentOverviewModule;

  beforeEach(() => {
    documentOverviewModule = new DocumentOverviewModule();
  });

  it('should create an instance', () => {
    expect(documentOverviewModule).toBeTruthy();
  });
});
