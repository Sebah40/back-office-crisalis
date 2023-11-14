import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { Item } from './item';

@Component({
  selector: 'app-menu-item',
  templateUrl: './menu-item.component.html',
  styleUrls: ['./menu-item.component.css']
})
export class MenuItemComponent {
  @Input() imagenUrl!: string;
  imagenDefaultUrl: string = '../assets/icons/arrow-right-circle.svg';
  @Input() item!: Item;
  @Output() callback: EventEmitter<any> = new EventEmitter();

  @ViewChild('opciones') opciones!: ElementRef;
  @ViewChild('icono') icono!: ElementRef;

  toggleMenuItem(){
    if(this.item.items.length !== 0) {
      this.opciones.nativeElement.classList.toggle("show");
      this.icono.nativeElement.classList.toggle("girar");
    }
  }

  toggleMenu() {
    this.callback.emit();
  }

  hasItems(item: Item):boolean {
    return this.item.items.length > 0;
  }

}
