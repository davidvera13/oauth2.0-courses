import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ResourcesService {

  constructor(private http: HttpClient) { }

  user(): Observable<any> {
    // http://127.0.0.1:8765/api/v1/resources/users
    return this.http.get<any>(
      "/api/v1/resources/users"
    );
  }

  admin(): Observable<any> {
    return this.http.get<any>(
      "/api/v1/resources/admins"
    );
  }
}
