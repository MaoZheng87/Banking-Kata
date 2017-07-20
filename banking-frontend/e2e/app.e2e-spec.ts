import { BankingFrontendPage } from './app.po';

describe('banking-frontend App', () => {
  let page: BankingFrontendPage;

  beforeEach(() => {
    page = new BankingFrontendPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
