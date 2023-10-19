import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {

  title = 'Home';

 
  theme?:string;
  preferedColorScheme = window.matchMedia('(prefers-color-scheme: light)').matches ? 'light' : 'dark';  
  changeTheme = () => {
    if(localStorage.getItem('theme') !== undefined) {
      this.theme = JSON.stringify(localStorage.getItem('theme'));
    } else {
      this.theme = this.preferedColorScheme;
    }
    this.preferedColorScheme = this.theme ;
  }

}
