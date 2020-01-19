export class AccommodationCategory {
    constructor(
        public id: number,
        public name: string
    ) { }
}

export class CreateAccommodationCategoryRequest {
    name: string;
}

export class UpdateAccommodationCategoryRequest {
    oldName: string;
    newName: string;
}

export class DeleteAccommodationCategoryRequest {
    name: string;
}

export class CodebookResponse {
    feedback: string;
}