import { Address, AgentAddress } from './address.model';

describe('Address', () => {
  it('should create an instance', () => {
    expect(new Address()).toBeTruthy();
  });
});

describe('AgentAddress', () => {
  it('should create an instance', () => {
    expect(new AgentAddress()).toBeTruthy();
  });
});
