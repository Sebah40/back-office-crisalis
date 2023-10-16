import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-upperbar',
  templateUrl: './upperbar.component.html',
  styleUrls: ['./upperbar.component.css']
})
export class UpperbarComponent {
  @Input() theme?:string;
  @Output() themeChanger = new EventEmitter<string>();
  sun:any;
  moon:any
  
  showSun:string = localStorage.getItem("theme") != 'light' ? 'block' : 'none'
  showMoon:string = localStorage.getItem("theme") != 'dark' ? 'block' : 'none'
  changeTheme() {
    this.sun = document.querySelector("#sun");
    this.moon = document.querySelector("#moon");
    if(localStorage.getItem("theme") === 'dark') {
      this.theme = 'light';   
      this.sun.style.display = 'none';
      this.moon.style.display = 'block';
    } else {
      this.theme = 'dark'
      this.sun.style.display = 'block';
      this.moon.style.display = 'none';
    }

    localStorage.setItem("theme", this.theme);
    document.documentElement.setAttribute("data-theme", localStorage.getItem("theme") || "dark");
    this.themeChanger.emit(this.theme);
  }

}
