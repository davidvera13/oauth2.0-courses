import { Component } from '@angular/core';
import {OauthService} from '../../../services/security/oauth.service';
import {ActivatedRoute} from '@angular/router';
import {AuthTokenResponse} from '../../../domains/auth/authTokenResponse';

@Component({
  selector: 'app-authorized',
  imports: [],
  templateUrl: './authorized.component.html',
  styleUrl: './authorized.component.css'
})
export class AuthorizedComponent {
  code!: string;
  accessToken!: string;

  constructor(
    private oauthService: OauthService,
    private activatedRoute: ActivatedRoute
  ) {
    this.activatedRoute.queryParams.subscribe(params => {
      this.code = params['code'];
      this.getToken()
    })
  }

  private getToken() {
    this.oauthService.getToken(this.code)
      .subscribe({
        next: (authTokenResponse: AuthTokenResponse) => {
          this.accessToken = authTokenResponse.accessToken;
        },
        error: error => {
          console.log(error);
        }
      })
  }
}
