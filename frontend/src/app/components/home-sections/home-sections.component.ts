import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-home-sections',
  templateUrl: './home-sections.component.html',
  styleUrls: ['./home-sections.component.css'],
})
export class HomeSectionsComponent {
  @Input() imagen?: string;
  @Input() color?: string;
  @Input() nombre?: string;
  @Input() path?: string;
}
