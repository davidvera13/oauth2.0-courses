import {Component, OnInit} from '@angular/core';
import {RouterLink} from '@angular/router';
import {environment} from '../../../../../environments/environment.development';
import {HttpParams} from '@angular/common/http';
import {TokenService} from '../../../../services/security/token.service';
import pkceChallenge from 'pkce-challenge';

@Component({
  selector: 'app-top-menu',
  imports: [
    RouterLink
  ],
  templateUrl: './top-menu.component.html',
  styleUrl: './top-menu.component.css'
})
export class TopMenuComponent implements OnInit {
  authorizeUri: string;
  logoutUri: string;
  params: any;
  isLogged!: boolean;
  isAdmin!: boolean;
  isUser!: boolean;


  constructor(
    private tokenService: TokenService
  ) {
    this.authorizeUri = environment.authorizeUri;
    this.logoutUri = environment.logoutUri;

    const codes = this.generateCodes();
    codes.then(res => {
      const codeChallenge = res.code_challenge;
      const codeVerifier = res.code_verifier;
      this.tokenService.setVerifier(codeVerifier);
      this.params = {
        client_id: environment.clientId,
        redirect_uri: environment.redirectUri,
        scope: environment.scope,
        response_type: environment.responseType,
        response_mode: environment.responseMode,
        code_challenge_method: environment.codeChallengeMethod,
        code_challenge: codeChallenge
        // code_challenge: environment.codeChallenge
      }
    });
  }

  ngOnInit(): void {
    this.getLogged();
  }

  onLogin(): void {
    const httpParams = new HttpParams({fromObject: this.params});
    //const codeUrl = this.authorizeUri + httpParams.toString();
    //location.href = codeUrl;
    location.href = this.authorizeUri + httpParams.toString();
  }

  onLogout(): void {
    this.tokenService.clear();
    location.href = this.logoutUri;

  }

  getLogged(): void {
    this.isLogged = this.tokenService.isLoggedIn();
    this.isAdmin = this.tokenService.isAdmin();
    this.isUser = this.tokenService.isUser();

    console.log(this.isAdmin);
  }

  async generateCodes(): Promise<any> {
    return await pkceChallenge();
  }
}
