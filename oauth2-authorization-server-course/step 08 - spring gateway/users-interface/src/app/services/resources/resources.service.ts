import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from '../../../environments/environment.development';
import {MessageResponse} from '../../domains/resources/resources/messageResponse';

@Injectable({
  providedIn: 'root'
})
export class ResourcesService {
  resourcesUrl: string;
  constructor(private httpClient: HttpClient) {
    this.resourcesUrl = environment.resources.resourcesUrl;
  }

  public getUserMessage(): Observable<MessageResponse> {
    return this.httpClient.get<MessageResponse>(this.resourcesUrl + '/users')
  }

  public getAdminMessage(): Observable<MessageResponse> {
    return this.httpClient.get<MessageResponse>(this.resourcesUrl + '/admins')
  }

  public getAnyMessage(): Observable<MessageResponse> {
    return this.httpClient.get<MessageResponse>(this.resourcesUrl + '/any')
  }
}
