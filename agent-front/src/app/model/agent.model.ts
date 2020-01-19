import { User } from './user.model';

export class Agent extends User{

   
    firstname: string="";
    lastname: string="";
    brn: number;
    constructor(){
        super();
    }
  
   
}