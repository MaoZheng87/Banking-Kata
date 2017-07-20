import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import "rxjs/add/operator/map";
import {Observable} from "rxjs/Observable";

@Injectable()
export class ApiService {

  constructor(private http: Http) {
  }

  public getAccounts(): Observable<any> {
    return this.http.get('http://localhost:8080/accounts')
      .map(response => response.json())
  }

}
