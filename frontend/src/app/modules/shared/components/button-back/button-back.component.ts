import { Location } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-button-back',
  templateUrl: './button-back.component.html',
  styleUrls: ['./button-back.component.css'],
})
export class ButtonBackComponent {
  constructor(private location: Location, private router: Router) {}
  @Input() isStraight: boolean = false;
  @Input() path: string = '';
  goBack() {
    this.path === '' ? this.location.back() : this.router.navigate([this.path]);
  }
}
