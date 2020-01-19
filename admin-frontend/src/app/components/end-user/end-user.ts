export class EndUser{
    constructor(
        public id: number,
        public email: string,
        public firstName: string,
        public lastName: string,
        public password: string,
        public username: string,
        public status: number
    ) {}
}

export class UserResponse { 
    feedback: string;
}