import {Component, OnInit, ViewChild} from '@angular/core';
import {NavigationEnd, Router, RouterOutlet} from '@angular/router';
import {TopMenuComponent} from './components/commons/menu/top-menu/top-menu.component';
import {filter} from 'rxjs';

@Component({
  selector: 'app-root',
  imports: [TopMenuComponent, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'users-interface';
  @ViewChild('menu') topMenuComponent!: TopMenuComponent;

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd))
      .subscribe(event => {
        this.topMenuComponent.getLogged();
      });
  }
}


