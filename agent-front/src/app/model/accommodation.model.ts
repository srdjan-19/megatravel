import { AccommodationType } from './accommodationType.model';
import { AccommodationCategory } from './accommodationCategory.model';
import { Address } from './address.model';
import { AdditionalService } from './additionalservice.model';
import { Cancellation, CancellationRequest } from './cancellation.model';
import { PriceInSeason, PriceRequest } from './priceInSeason.model';

export class Accommodation {
    id: number;
    name: string;
    address: Address = new Address();
    additionalService: AdditionalService[] = [];
    type: AccommodationType = new AccommodationType();
    category: AccommodationCategory = new AccommodationCategory();
    description: string;
    cancelation = new Cancellation();
    priceInSeason: PriceInSeason[] = [];
    capacity: number;
}

export class CreateAccommodationRequest {
    name: string
    type: string
    category: string
    description: string
    address: Address
    capacity: number
    cancellation = new CancellationRequest()
    pricelist: PriceRequest[] = []
    additionalServices: string[] = []
    images: string[] = []
}

export class UpdateAccommodationRequest {
    id: number;
    name: string
    type: string
    category: string
    description: string
    capacity: number
}