export class User {
    id: string;
    username: string;
    password: string;
    firstName: string;
    lastName: string;
    email: string;
    role: string[];

    onstructor(id: string, username: string, email: string, password: string, firstName: string, lastName: string) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = ['user'];
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
