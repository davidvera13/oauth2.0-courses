import { Routes } from '@angular/router';
import {HomeComponent} from './components/pages/home/home.component';
import {AuthorizedComponent} from './components/pages/authorized/authorized.component';
import {LogoutComponent} from './components/pages/auth/logout/logout.component';
import {AnyComponent} from './components/pages/users/any/any.component';
import {UserComponent} from './components/pages/users/user/user.component';
import {AdminComponent} from './components/pages/users/admin/admin.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
  },
  {
    path: 'authorized',
    component: AuthorizedComponent,
  },
  {
    path: "users",
    component: UserComponent
  },
  {
    path: "admins",
    component: AdminComponent
  },
  {
    path: "any",
    component: AnyComponent
  },
  {
    path: "logout",
    component: LogoutComponent
  },

  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full'
  }
];
