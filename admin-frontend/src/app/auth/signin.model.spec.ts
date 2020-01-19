import { SigninRequest, SigninResponse } from './signin.model';

describe('SigninRequest', () => {
  it('should create an instance', () => {
    expect(new SigninRequest()).toBeTruthy();
  });
});

describe('SigninResponse', () => {
  it('should create an instance', () => {
    expect(new SigninResponse()).toBeTruthy();
  });
});
