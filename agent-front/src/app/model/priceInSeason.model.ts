import { Currencies } from './currencies.enum';
import { Months } from './months.enum'; 

export class PriceInSeason{
    id:number;
    inMonth:Months;
    price:number;
    currency:string;
    accId: number;
}

export class PriceRequest {
    month: Months;
    price: number;
    currency: Currencies;
}