import { Component, OnInit } from '@angular/core';
import { Login } from 'src/app/model/login';
import { TokenService } from 'src/app/modules/auth/service/token.service';
import { AuthService } from 'src/app/modules/auth/service/auth.service';
import { Router } from '@angular/router';
import { UpperbarComponent } from '../../../../components/upperbar/upperbar.component';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import Swal from 'sweetalert2';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  isLogged = false;
  isLoginFail = false;
  login!: Login;
  username!: string;
  password!: string;
  roles: string[] = [];
  errMsj!: string;
  constructor(
    private tokenService: TokenService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (this.tokenService.getToken()) {
      this.isLogged = true;
      this.isLoginFail = false;
      this.roles = this.tokenService.getAuthorities();
    }
  }

  onLogin(): void {
    this.login = new Login(this.username, this.password);
    this.authService.login(this.login).subscribe(
      (data) => {
        this.isLogged = true;
        this.isLoginFail = false;
        this.tokenService.setToken(data.token);
        this.tokenService.setUserName(this.username);
        UpperbarComponent;
        this.tokenService.setAuthorities(data.authorities);
        this.roles = data.authorities;
        this.router.navigate(['/home']).then(() => {
          window.location.reload();
        });
      },
      (err) => {
        Swal.fire(
          'combinación usuario/contraseña incorrectos',
          undefined,
          'error'
        );
        this.isLogged = false;
        this.isLoginFail = true;
        this.errMsj = err.error.mensaje;
        console.log(this.errMsj);
      }
    );
  }
}
