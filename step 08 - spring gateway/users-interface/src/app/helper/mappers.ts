import {AuthTokenResponse} from '../domains/auth/authTokenResponse';

export function mapTokenResponse(raw: any): AuthTokenResponse {
  return {
    accessToken: raw.access_token,
    refreshToken: raw.refresh_token,
    scope: raw.scope,
    idToken: raw.id_token,
    tokenType: raw.token_type,
    expiresIn: raw.expires_in,
  };
}
