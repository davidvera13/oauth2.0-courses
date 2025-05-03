import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {TokenService} from '../../../../services/security/token.service';

@Component({
  selector: 'app-logout',
  imports: [],
  templateUrl: './logout.component.html',
  styleUrl: './logout.component.css'
})
export class LogoutComponent {
  constructor(
    private router: Router,
    private tokenService: TokenService) {
    tokenService.clear();
    this.router.navigate(['..']).then();
  }
}
