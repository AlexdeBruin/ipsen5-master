import {ProgressRoutingModule} from "./progress-routing.module";

describe('ProgressRoutingModule', () => {
  let progressRoutingModule: ProgressRoutingModule;

  beforeEach(() => {
    progressRoutingModule = new ProgressRoutingModule();
  });

  it('should create an instance', () => {
    expect(progressRoutingModule).toBeTruthy();
  });
});
