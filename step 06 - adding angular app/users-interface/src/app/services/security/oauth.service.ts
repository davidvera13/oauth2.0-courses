import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {map, Observable, Observer} from 'rxjs';
import {environment} from '../../../environments/environment.development';
import {AuthTokenResponse} from '../../domains/auth/authTokenResponse';
import {mapTokenResponse} from '../../helper/mappers';

@Injectable({
  providedIn: 'root'
})
export class OauthService {
  tokenUri!: string;

  constructor(
    private http: HttpClient,
  ) {
  }

  /**
   * we must check code challenge is validated by the verifier
   * @param code
   */
  /**
   * we must check code challenge is validated by the verifier
   * @param code
   */
  getToken(code: string): Observable<AuthTokenResponse> {
    let body = new URLSearchParams();
    body.set('grant_type', environment.grantType);
    body.set('client_id', environment.clientId);
    body.set('redirect_uri', environment.redirectUri);
    body.set('code_verifier', environment.codeVerifier);
    body.set('code', code);

    const auth = 'Basic ' + btoa('client:secret');
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Accept': '*/*',
      'Authorization': auth
    });
    const options = { headers: headers };

    return this.http
      .post<AuthTokenResponse>(
        environment.tokenUri, body, options)
      .pipe(map(mapTokenResponse)); // Transform to camelCase here;
  }

}
