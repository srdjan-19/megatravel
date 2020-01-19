import { Address } from '../../model/address.model';

export class Agent {
    id: number
    username: string
    password: string
    email: string
    firstName: string
    lastName: string
    address: Address
    brn: number
}

export class CreateAgentRequest {
    username: string;
    password: string;
    email: string;
    firstName: string;
    lastName: string;
    addressId: number;
    brn: number;
}