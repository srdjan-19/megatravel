export class SigninRequest {
    username: string;
    password: string;
}

export class SigninResponse {
    accessToken: string;
    type: string;
    username: string;
    grantedAuthorities: string[];
}
