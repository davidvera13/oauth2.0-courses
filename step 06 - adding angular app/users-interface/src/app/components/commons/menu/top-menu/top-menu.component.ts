import { Component } from '@angular/core';
import {Router} from '@angular/router';
import { environment } from '../../../../../environments/environment.development';
import {HttpParams} from '@angular/common/http';

@Component({
  selector: 'app-top-menu',
  imports: [],
  templateUrl: './top-menu.component.html',
  styleUrl: './top-menu.component.css'
})
export class TopMenuComponent {
  authorizeUri: string;
  params: any;

  constructor(
    private router: Router
  ) {
    this.authorizeUri = environment.authorizeUri;
    this.params = {
      client_id: environment.clientId,
      redirect_uri: environment.redirectUri,
      scope: environment.scope,
      response_type: environment.responseType,
      response_mode: environment.responseMode,
      code_challenge_method: environment.codeChallengeMethod,
      code_challenge: environment.codeChallenge
    }

  }

  ngOnInit(): void {
  }

  onLogin(): void {
    const httpParams = new HttpParams({fromObject: this.params});
    //const codeUrl = this.authorizeUri + httpParams.toString();
    //location.href = codeUrl;
    location.href = this.authorizeUri + httpParams.toString();
  }

}
