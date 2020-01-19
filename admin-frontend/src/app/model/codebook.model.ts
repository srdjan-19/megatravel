export class Codebook {
    id: string;
    name: string;
}

export class CreateCodebookItemRequest {
    name: string;
}

export class UpdateCodebookItemRequest {
    oldName: string;
    newName: string;
}

export class DeleteCodebookItemRequest {
    name: string;
}

export class CodebookResponse {
    feedback: string;
}