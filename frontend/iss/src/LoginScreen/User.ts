export interface UserParameters{
    username: String;
    password: String;
    role?: String;
    name?: String;
}

export class User{
    private username: String;
    private password: String;
    private role: String;
    protected name: String;

    public constructor(parameters: UserParameters){
        this.username = parameters.username;
        this.password = parameters.password;
        this.role = parameters.role;
        this.name = parameters.name;
    }


    //TODO get set tostring
}