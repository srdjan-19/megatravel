export class SignupRequest {
    username: string;
    password: string;
    firstName: string;
    lastName: string;
    email: string;
}

export class SignupResponse {
    feedback: string;
}