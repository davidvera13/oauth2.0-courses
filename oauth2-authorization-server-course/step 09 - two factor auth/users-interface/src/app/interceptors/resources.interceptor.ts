import { HttpInterceptorFn } from '@angular/common/http';
import {inject} from '@angular/core';
import {TokenService} from '../services/security/token.service';

export const resourcesInterceptor: HttpInterceptorFn = (req, next) => {
  const tokenService = inject(TokenService);
  const token = tokenService.getAccessToken();
  let request = req;
  if (token != null && req.url.includes('resources'))
    request = req.clone({headers: req.headers.set('Authorization', 'Bearer ' + token)})
  return next(request);
};
