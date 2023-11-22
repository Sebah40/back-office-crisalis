import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'Home';
  isLoginPath: boolean = false;
  theme?: string;
  preferedColorScheme = window.matchMedia('(prefers-color-scheme: light)')
    .matches
    ? 'light'
    : 'dark';

  constructor(
    private location: Location,
    private translate: TranslateService,
    private router: Router
  ) {
    translate.setDefaultLang('es');
  }

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        const path: string = this.location.path() || '';
        const isLogin: string = path.split('/')[1];
        this.isLoginPath = isLogin == 'login';
      }
    });

    // this.changeTheme();
  }

  changeTheme = () => {
    if (localStorage.getItem('theme') !== undefined) {
      this.theme = JSON.stringify(localStorage.getItem('theme'));
    } else {
      this.theme = this.preferedColorScheme;
    }
    this.preferedColorScheme = this.theme;
  };
}
