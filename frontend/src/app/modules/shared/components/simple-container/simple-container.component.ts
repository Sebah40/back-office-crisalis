import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-simple-container',
  templateUrl: './simple-container.component.html',
  styleUrls: ['./simple-container.component.css']
})
export class SimpleContainerComponent {
  @Input() title = "";
}
