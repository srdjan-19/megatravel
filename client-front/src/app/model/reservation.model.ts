import { Accommodation } from "./accommodation.model";

export class Reservation {
    accommodation: Accommodation;
    fromDate: Date;
    tillDate: Date;
    status: String;
}

export class CreateReservationRequest {
    accommodationName: string;
    fromDate: Date;
    tillDate: Date;
}

export class CudReservationResponse {
    feedback: string;
}

export class CancelReservationRequest {
    accommodationId: string;
}
