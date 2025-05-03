export interface AuthTokenResponse {
  accessToken: string;
  refreshToken: string;
  scope: string;
  idToken: string;
  tokenType: string;
  expiresIn: number;
}
