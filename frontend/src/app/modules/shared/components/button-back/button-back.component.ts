import { Location } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-button-back',
  templateUrl: './button-back.component.html',
  styleUrls: ['./button-back.component.css'],
})
export class ButtonBackComponent {
  constructor(private location: Location) {}
  @Input() isStraight: boolean = false;
  goBack() {
    this.location.back();
  }
}
