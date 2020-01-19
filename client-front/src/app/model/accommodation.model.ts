import { AccommodationType } from "./accommodation-type.model";
import { AccommodationCategory } from "./accommodation-category.model";
import { Address } from "./address.model";
import { AdditionalService } from "./additional-service.model";
import { PriceInSeason } from "./price-in-season.model";
import { Agent } from "./agent.model";
import { Comment } from "./comment.model";
import { ImageResource } from "./image-resource.model";
import { Cancellation } from "./cancellation.model";


export class Accommodation {
    name: string;
    type: AccommodationType;
    category: AccommodationCategory;
    ownedBy: Agent;
    distance: number;
    description: string;
    image: ImageResource[];
    address: Address;
    capacity: number;
    additionalServices: AdditionalService[];
    cancellation: Cancellation;
    priceInSeason: PriceInSeason[];
    rate: number;
    comments: Comment[];
}

export class SearchAccommodationRequest {
    name: string;
    type: string;
    category: string;

    constructor(name: string, type: string, category: string) {
        this.name = name;
        this.type = type;
        this.category = category;
    }
}

