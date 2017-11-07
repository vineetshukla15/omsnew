
import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams, ResponseContentType } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { LoaderService } from './loader.service';

// Import RxJs required methods
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class HttpService {
    // Resolve HTTP using the constructor
    constructor(private http: Http, private loaderService: LoaderService) { }

    // GET REQUEST

    getWithAuth(url): Observable<any> {
        this.loaderService.display(true);
        let headers = new Headers({ 'Content-Type': 'application/json' });
        //Set content type to JSON
        let options = new RequestOptions({ headers: headers }); // Create a request optio
        //using get request
        return this.http.get(url, options)
            // and calling .json() on the response to return data
            .map((res: Response) => {
                this.loaderService.display(false);
                return res.json();
            })
            // errors if any
            .catch((error: any) => { 
                this.loaderService.display(false);
                return Observable.throw(error.json().error || error.json().message || 'Server error')
            });

    }

    get(url, token): Observable<any> {

        this.loaderService.display(true);

        let headers = new Headers({ 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + token });
        //Set content type to JSON
        let options = new RequestOptions({ headers: headers }); // Create a request optio
        //using get request
        return this.http.get(url, options)
            // and calling .json() on the response to return data
            .map((res: Response) => {
                this.loaderService.display(false);
                return res.json();
            })
            // errors if any
            .catch((error: any) => { 
                this.loaderService.display(false);
                return Observable.throw(error.json().error || error.json().message || 'Server error')
            });

    }

        getFile(url, token): Observable<any> {
        this.loaderService.display(true);
        let headers = new Headers({'Authorization': 'Bearer ' + token });
        //Set content type to JSON
        let options = new RequestOptions({ headers: headers, responseType: ResponseContentType.Blob  }); // Create a request optio
        //using get request
        return this.http.get(url, options)
            // and calling .json() on the response to return data
            // and calling .json() on the response to return data
            .map((res: Response) => {
                this.loaderService.display(false);
                return res;
            })
            // errors if any
            .catch((error: any) => { 
                this.loaderService.display(false);
                return Observable.throw(error.json().error || error.json().message || 'Server error')
            });

    }

    // GET REQUEST
    getXML(url): Observable<any> {
        this.loaderService.display(true);
        //using get request
        return this.http.get(url)
            // and calling .json() on the response to return data
            .map((res: Response) => {
                this.loaderService.display(false);
                return res.text();
            })
            // errors if any
            .catch((error: any) => { 
                this.loaderService.display(false);
                return Observable.throw('Server error')
            });

    }

    postWithoutAuth(url, obj: Object): Observable<any> {
        this.loaderService.display(true);
        let body = JSON.stringify(obj); // Stringify payload
        let headers = new Headers({ 'Content-Type': 'application/json' });

        //Set content type to JSON
        let options = new RequestOptions({ headers: headers }); // Create a request option

        //let body = this.serialize(obj,'').toString();
        //let headers = new Headers({ 'Content-Type': 'application/x-www-form-urlencoded' }); //Set content type to x-www-form-urlencoded
        //let options = new RequestOptions({ headers: headers }); // Create a request option
        return this.http.post(url, body, options) //using post request
            .map((res: Response) => {
                this.loaderService.display(false);
                return res.json();
            }) //and calling .json() on the response to return data
            .catch(this.handleError); //errors if any
    }
    //POST REQUEST
    postOAUTH(url, obj: Object): Observable<any> {
        //let body = JSON.stringify(obj); // Stringify payload
        this.loaderService.display(true);

        let body = 'username=' + obj['userName'] + '&password=' + obj['password'] + '&grant_type=password&scope=read%20write&client_secret=omspass&client_id=omsapp';
        let headers = new Headers({ 'Content-Type': 'application/x-www-form-urlencoded ' });
        headers.append('Authorization', 'Basic ' + btoa('omsapp:omspass'));

        let options = new RequestOptions({ headers: headers }); // Create a request option       

        return this.http.post(url, body, options) //using post request
            .map((res: Response) => {
                this.loaderService.display(false);
                return res.json();
            }) //and calling .json() on the response to return data
            .catch(this.handleError); //errors if any
    }

    post(url, obj: Object, token): Observable<any> {
        this.loaderService.display(true);
        let body = JSON.stringify(obj); // Stringify payload
        let headers = new Headers({ 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + token });

        //Set content type to JSON
        let options = new RequestOptions({ headers: headers }); // Create a request option

        //let body = this.serialize(obj,'').toString();
        //let headers = new Headers({ 'Content-Type': 'application/x-www-form-urlencoded' }); //Set content type to x-www-form-urlencoded
        //let options = new RequestOptions({ headers: headers }); // Create a request option
        return this.http.post(url, body, options) //using post request
            .map((res: Response) => {
                this.loaderService.display(false);
                return res.json();
            }) //and calling .json() on the response to return data
            .catch((error: any) => { 
                this.loaderService.display(false);
                return Observable.throw(error.json().error || error.json().message || 'Server error')
            });
    }


     postFileUpload(url, obj: Object, token): Observable<any> {
        this.loaderService.display(true);
        //let body = JSON.stringify(obj); // Stringify payload
        let headers = new Headers({"Authorization": "Bearer " + token});

        //Set content type to JSON
        let options = new RequestOptions({ headers: headers }); // Create a request option

        //let body = this.serialize(obj,'').toString();
        //let headers = new Headers({ 'Content-Type': 'application/x-www-form-urlencoded' }); //Set content type to x-www-form-urlencoded
        //let options = new RequestOptions({ headers: headers }); // Create a request option
        return this.http.post(url,obj,options) //using post request
            .map((res: Response) => {
                this.loaderService.display(false);
                return res.json();
            }) //and calling .json() on the response to return data
            .catch((error: any) => { 
                this.loaderService.display(false);
                return Observable.throw(error.json().error || error.json().message || 'Server error')
            });
    }


    putFileUpload(url, obj: Object, token): Observable<any> {
        this.loaderService.display(true);
        //let body = JSON.stringify(obj); // Stringify payload
        let headers = new Headers({"Authorization": "Bearer " + token});

        //Set content type to JSON
        let options = new RequestOptions({ headers: headers }); // Create a request option

        //let body = this.serialize(obj,'').toString();
        //let headers = new Headers({ 'Content-Type': 'application/x-www-form-urlencoded' }); //Set content type to x-www-form-urlencoded
        //let options = new RequestOptions({ headers: headers }); // Create a request option
        return this.http.post(url,obj,options) //using post request
            .map((res: Response) => {
                this.loaderService.display(false);
                return res.json();
            }) //and calling .json() on the response to return data
            .catch((error: any) => { 
                this.loaderService.display(false);
                return Observable.throw(error.json().error || error.json().message || 'Server error')
            });
    }

    putWithourAuth(url, obj: Object): Observable<any> {
        this.loaderService.display(true);
        let body = JSON.stringify(obj); // Stringify payload

        let headers = new Headers({ 'Content-Type': 'application/json' });

        //Set content type to JSON
        let options = new RequestOptions({ headers: headers }); // Create a request option

        //let body = this.serialize(obj,'').toString();
        //let headers = new Headers({ 'Content-Type': 'application/x-www-form-urlencoded' }); //Set content type to x-www-form-urlencoded
        //let options = new RequestOptions({ headers: headers }); // Create a request option

        return this.http.put(url, body, options) //using put request
            .map((res: Response) => {
                this.loaderService.display(false);
                return res.json();
            }) //and calling .json() on the response to return data
            .catch((error: any) => { 
                this.loaderService.display(false);
                return Observable.throw(error.json().error || error.json().message || 'Server error')
            });
    }
    //PUT Request
    put(url, obj: Object, token): Observable<any> {
        this.loaderService.display(true);
        let body = JSON.stringify(obj); // Stringify payload

        let headers = new Headers({ 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + token });

        //Set content type to JSON
        let options = new RequestOptions({ headers: headers }); // Create a request option

        //let body = this.serialize(obj,'').toString();
        //let headers = new Headers({ 'Content-Type': 'application/x-www-form-urlencoded' }); //Set content type to x-www-form-urlencoded
        //let options = new RequestOptions({ headers: headers }); // Create a request option

        return this.http.put(url, body, options) //using put request
            .map((res: Response) => {
                this.loaderService.display(false);
                return res.json();
            }) //and calling .json() on the response to return data
            .catch((error: any) => { 
                this.loaderService.display(false);
                return Observable.throw(error.json().error || error.json().message || 'Server error')
            });
    }

    // DELETE Request
    deleteWithId(url, token): Observable<any> {
        this.loaderService.display(true);
        let headers = new Headers({ 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + token });
        //Set content type to JSON
        let options = new RequestOptions({ headers: headers }); // Create a request option
        return this.http.delete(url, options) //using delete request
            .map((res: Response) => this.checkDeleteStatus(res)) //and calling .json() on the response to return data
            .catch((error: any) => { 
                this.loaderService.display(false);
                return Observable.throw(error.json().error || error.json().message || 'Server error')
            });
    }

    // DELETE Request
    delete(url, obj, token): Observable<any> {
        this.loaderService.display(true);
        let headers = new Headers({ 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + token });
        let body = JSON.stringify(obj); // Stringify payload
        //Set content type to JSON
        let options = new RequestOptions({ headers: headers, body: body }); // Create a request option
        return this.http.delete(url, options) //using delete request
            .map((res: Response) => this.checkDeleteStatus(res)) //and calling .json() on the response to return data
            .catch((error: any) => { 
                this.loaderService.display(false);
                return Observable.throw(error.json().error || error.json().message || 'Server error')
            });
    }

    checkDeleteStatus(res) {
        this.loaderService.display(false);
        var statusJson = {
            "staus": true
        }
        if (res.status === 200 || res.status === 204) {
            return [{ status: res.status, statusJson }]
        }

    }
    //serialize
    serialize(obj, prefix) {
        prefix = prefix || '';
        var str = [], p;
        for (p in obj) {
            if (obj.hasOwnProperty(p)) {
                var k = prefix ? prefix + '[' + p + ']' : p, v = obj[p];
                str.push((v !== null && typeof v === 'object') ?
                    this.serialize(v, k) :
                    encodeURIComponent(k) + '=' + encodeURIComponent(v));
            }
        }
        return str.join('&');
    };


    /**
   * Handle any errors from the API
   */
    private handleError(err) {
        let errMessage: string;

        this.loaderService.display(false);

        if (err instanceof Response) {
            let body = err.json() || '';
            let error = body.error || JSON.stringify(body);
            errMessage = `${err.status} - ${err.statusText || ''} ${error}`;
        } else {
            errMessage = err.message ? err.message : err.toString();
        }

        return Observable.throw(errMessage);
    }
}
