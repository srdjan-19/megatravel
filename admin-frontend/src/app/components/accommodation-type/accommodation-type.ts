export class AccommodationType {
    id: number;
    name: string;
}

export class CreateAccommodationTypeRequest {
    name: string;
}

export class UpdateAccommodationTypeRequest {
    oldName: string;
    newName: string;
}

export class DeleteAccommodationTypeRequest {
    name: string;
}

export class CodebookResponse {
    feedback: string;
}