import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {ResourcesService} from "./services/resources.service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  messageUser!: string;
  messageAdmin!: string

  constructor(
    private service: ResourcesService) {
  }

  onLogin(): void {
    // http://localhost:9000/oauth2/authorize?
    //    client_id=oidc-client&
    //    redirect_uri=https://oauthdebugger.com/debug&
    //    scope=openid&
    //    response_type=code&
    //    response_mode=form_post&
    //    code_challenge_method=S256&
    //    code_challenge=ZMqsTsK3mcijkFnD2...SPNQ&
    //    state=2umhguvv7eq&
    //    nonce=51zgiwz11f5
    //window.location.href = 'http://localhost:9000/';
    window.location.href = 'http://127.0.0.1:8765/oauth2/authorization/gateway';

  }

  onLogout() {
    window.location.href = "/logout";
  }

  user(): void {
    this.service.user().subscribe({
      next: (res: { message: string; }) => {
        console.log(res);
        this.messageUser = res.message
      },
      error: (err: { statusText: string; status: string; }) => this.messageUser = err.statusText + ": " + err.status,
      complete: () => console.log('Completed')
    })
  }

  admin(): void {
    this.service.admin().subscribe({
      next: res => {
        console.log(res);
        this.messageAdmin = res.message
      },
      error: err => this.messageAdmin = err.statusText + ": " + err.status,
      complete: () => console.log('Completed')
    })
  }


}

