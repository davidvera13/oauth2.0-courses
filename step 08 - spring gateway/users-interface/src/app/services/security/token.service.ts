import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment.development";
import CryptoJS from "crypto-js";


const ACCESS_TOKEN = "access_token";
const REFRESH_TOKEN = "refresh_token";
const CODE_VERIFIER = "code_verifier";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }

  setToken(accessToken: string, refreshToken: string) {
    localStorage.removeItem(ACCESS_TOKEN);
    localStorage.removeItem(REFRESH_TOKEN);
    localStorage.setItem(ACCESS_TOKEN, accessToken);
    localStorage.setItem(REFRESH_TOKEN, refreshToken);
  }

  getAccessToken(): string | null {
    return localStorage.getItem(ACCESS_TOKEN);
  }

  getRefreshToken(): string | null {
    return localStorage.getItem(REFRESH_TOKEN);
  }

  clear() {
    localStorage.removeItem(ACCESS_TOKEN);
    localStorage.removeItem(REFRESH_TOKEN);
  }

  isLoggedIn(): boolean {
    return localStorage.getItem(ACCESS_TOKEN) != null;
  }

  isAdmin(): boolean {
    if(!this.isLoggedIn())
      return false;
    const token = this.getAccessToken();
    const payload = token?.split(".")[1]; //// AAA.vvvv.cccc
    const decoded = atob(payload! );
    const values = JSON.parse(decoded);
    const roles = values["roles"];
    return roles.indexOf('ROLE_ADMIN') >= 0;
  }

  isUser(): boolean {
    if(!this.isLoggedIn())
      return false;
    const token = this.getAccessToken();
    const payload = token?.split(".")[1]; //// AAA.vvvv.cccc
    const decoded = atob(payload! );
    const values = JSON.parse(decoded);
    const roles = values["roles"];
    return roles.indexOf('ROLE_USER') >= 0;
  }

  /**
   * set the verifier
   * @param codeVerifier code verifier to store
   */
  setVerifier(codeVerifier: string): void {
    if(localStorage.getItem(CODE_VERIFIER))
      this.deleteVerifier();
    // localStorage.setItem(CODE_VERIFIER, codeVerifier);

    const encrypted = CryptoJS.AES
      .encrypt(codeVerifier, environment.secretPKCE);
    console.log("encrypted", encrypted)
    localStorage.setItem(CODE_VERIFIER, encrypted.toString());
  }


  /**
   * retrieve the verifier from local storage
   */
  getVerifier(): string {
    //return localStorage.getItem(CODE_VERIFIER)!;
    const encrypted = localStorage.getItem(CODE_VERIFIER)!;
    const decrypted = CryptoJS.AES.decrypt(encrypted, environment.secretPKCE)
      .toString(CryptoJS.enc.Utf8);
    console.log("decrypted", decrypted);
    return decrypted;
  }

  /**
   * remove code verifier from local storage
   */
  deleteVerifier() {
    localStorage.removeItem(CODE_VERIFIER);
  }
}
