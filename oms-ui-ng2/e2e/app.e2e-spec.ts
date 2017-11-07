import { HotstarUiNg2Page } from './app.po';

describe('hotstar-ui-ng2 App', () => {
  let page: HotstarUiNg2Page;

  beforeEach(() => {
    page = new HotstarUiNg2Page();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
