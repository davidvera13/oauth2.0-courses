import { Component } from '@angular/core';
import {OauthService} from '../../../services/security/oauth.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthTokenResponse} from '../../../domains/auth/authTokenResponse';
import {TokenService} from '../../../services/security/token.service';

@Component({
  selector: 'app-authorized',
  imports: [],
  templateUrl: './authorized.component.html',
  styleUrl: './authorized.component.css'
})
export class AuthorizedComponent {
  code!: string;
  accessToken!: string;
  refreshToken!: string;

  constructor(
    private oauthService: OauthService,
    private tokenService: TokenService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {
    this.activatedRoute.queryParams.subscribe(params => {
      this.code = params['code'];
      this.getToken();
    })
  }

  private getToken() {
    const codeVerifier = this.tokenService.getVerifier();
    this.tokenService.deleteVerifier();

    this.oauthService.getToken(this.code, codeVerifier)
      .subscribe({
        next: (authTokenResponse: AuthTokenResponse) => {
          this.accessToken = authTokenResponse.accessToken;
          this.refreshToken = authTokenResponse.refreshToken;
          this.tokenService.setToken(this.accessToken, this.refreshToken);
          this.router.navigate(['/']);
        },
        error: error => {
          console.log(error);
        }
      })
  }
}
