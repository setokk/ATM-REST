export class JwtUtils {
    static token = undefined;
    static user = undefined;

    static updateJwt(token, user) {
        const tokenJSON = this.parseJwt(token);

        user.id       = tokenJSON.id;
        user.username = tokenJSON.username;
        user.email    = tokenJSON.email;
        user.balance  = tokenJSON.balance; 

        this.token = token;
        this.user = user;
    }

    static parseJwt(token) {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(
          atob(base64)
            .split('')
            .map(function (c) {
              return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            })
            .join('')
        );
        
        return JSON.parse(jsonPayload);
      }
}