import { Routes } from '@angular/router';
import {HomeComponent} from './components/pages/home/home.component';
import {AuthorizedComponent} from './components/pages/authorized/authorized.component';

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
    path: '**',
    redirectTo: '',
    pathMatch: 'full'
  }
];
