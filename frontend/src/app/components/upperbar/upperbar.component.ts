import { Component, Input, Output, EventEmitter } from '@angular/core';
import { TokenService } from '../../modules/auth/service/token.service';
import { UserService } from 'src/app/modules/user/services/user.service';

@Component({
  selector: 'app-upperbar',
  templateUrl: './upperbar.component.html',
  styleUrls: ['./upperbar.component.css'],
})
export class UpperbarComponent {
  @Input() theme?: string;
  @Output() themeChanger = new EventEmitter<string>();
  sun: any;
  moon: any;

  showSun: string = localStorage.getItem('theme') != 'light' ? 'block' : 'none';
  showMoon: string = localStorage.getItem('theme') != 'dark' ? 'block' : 'none';

  constructor(
    private tokenService: TokenService,
    private userService: UserService
  ) {}
  isLogged = false;
  name = 'username';
  srcPhoto: string = '';
  ngOnInit(): void {
    if (this.tokenService.getToken()) {
      this.isLogged = true;
      this.name = this.tokenService.getUserName();
    } else {
      this.isLogged = false;
    }
    this.userService.getProfile().subscribe({
      next: (res) => {
        this.srcPhoto = res.photo;
      },
    });
  }
  onLogOut(): void {
    this.tokenService.logOut();
    window.location.reload();
  }

  changeTheme() {
    this.sun = document.querySelector('#sun');
    this.moon = document.querySelector('#moon');
    if (localStorage.getItem('theme') === 'dark') {
      this.theme = 'light';
      this.sun.style.display = 'none';
      this.moon.style.display = 'block';
    } else {
      this.theme = 'dark';
      this.sun.style.display = 'block';
      this.moon.style.display = 'none';
    }

    localStorage.setItem('theme', this.theme);
    document.documentElement.setAttribute(
      'data-theme',
      localStorage.getItem('theme') || 'dark'
    );
    this.themeChanger.emit(this.theme);
  }
}
