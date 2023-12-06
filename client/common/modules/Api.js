export class Api {
    static USERS    = 'http://localhost:8080/api/users';
    static REGISTER = this.USERS + '/register';
    static LOGIN    = this.USERS + '/login';
    static DEPOSIT  = this.USERS + '/deposit';
    static WITHDRAW = this.USERS + '/withdraw';
}