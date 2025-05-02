export const environment = {
  production: false,
  // built on url :
  // http://localhost:9000/oauth2/authorize?
  // client_id=client&
  // redirect_uri=http%3A%2F%2Flocalhost%3A4200%2Fauthorized&
  // scope=openid&
  // response_type=code&
  // response_mode=form_post&
  // code_challenge_method=S256&
  // code_challenge=HuTu7xT0V8hPJR5_njPQ3OEdv-AUA1ByGkKVoBSh_iE&
  // state=th62lairzh&
  // nonce=pvpsoxdnwng
  grantType: 'authorization_code',
  authorizeUri: 'http://localhost:9000/oauth2/authorize?',
  clientId: 'client',
  redirectUri: 'http://localhost:5200/authorized',
  scope: 'openid profile email',
  responseType: 'code',
  responseMode: 'form_post',
  codeChallengeMethod: 'S256',
  // state and nonce are ignored
  tokenUri: 'http://localhost:9000/oauth2/token',
  resources: {
    resourcesUrl: 'http://localhost:8787/api/v1/resources'
  },
  logoutUri: 'http://localhost:9000/logout',
  secretPKCE: '9PgT0RyD6ejy4XM2EUgpnGvFYQg6yS'
};
