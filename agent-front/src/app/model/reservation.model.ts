import { EndUser } from './endUser.model';
import { Accommodation } from './accommodation.model';

export class Reservation {
    id: number;
    accommodation: Accommodation;
    fromDate: Date;
    tillDate: Date;
    status: string;
}

export class UpdateReservationRequest {
    id: number;
    status: string;
}