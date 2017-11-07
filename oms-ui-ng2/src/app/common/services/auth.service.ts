
import { Injectable } from '@angular/core';
import { LocalStorageService } from "./local-storage.service";
import { HttpService } from "./http.service"
import { PathConfig } from "../config/path.config";
import { ConstantConfig } from "../config/constant.config";



@Injectable()
export class AuthService {
    constructor(private localStorage: LocalStorageService, private httpService: HttpService) { }

    isLoggedIn(): boolean {
        var data = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
        return data ? true : false;
    }

    /**
* Log the user in
*/
    login(credential) {
        let url = PathConfig.BASE_URL_API + PathConfig.OATH_TOKEN;
        return this.httpService.postOAUTH(url, credential);
    }
}
