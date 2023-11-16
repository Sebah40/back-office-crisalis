import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'Home';
  isLoginPath: boolean = false;
  theme?: string;
  preferedColorScheme = window.matchMedia('(prefers-color-scheme: light)')
    .matches
    ? 'light'
    : 'dark';

  constructor(private location: Location, private translate: TranslateService) {
    translate.setDefaultLang('es');
  }

  ngOnInit(): void {
    const path: string = this.location.path() || '';
    const isLogin: string = path.split('/')[1];
    this.isLoginPath = isLogin == 'login';
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
