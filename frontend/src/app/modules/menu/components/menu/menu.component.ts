import { Component, ElementRef, ViewChild } from '@angular/core';
import { Item } from '../menu-item/item';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css'],
})
export class MenuComponent {
  @ViewChild('menu') menu!: ElementRef;

  home: Item = {
    texto: 'Home',
    enlace: '/home',
    items: [],
  };

  items: Item[] = [
    {
      texto: 'Usuarios',
      enlace: '/user',
      items: [],
    },
    {
      texto: 'Pedidos',
      enlace: '',
      items: [
        {
          texto: 'Lista',
          enlace: '/order/getAll',
          items: [],
        },
        {
          texto: 'Crear pedido',
          enlace: '/order/create',
          items: [],
        },
      ],
    },
    {
      texto: 'Productos',
      enlace: '/good',
      items: [],
    },
    {
      texto: 'Clientes',
      enlace: '',
      items: [
        {
          texto: 'Personas',
          enlace: '/person',
          items: [],
        },
        {
          texto: 'Empresas',
          enlace: '/enterprise',
          items: [],
        },
        {
          texto: 'Suscripciones',
          enlace: '/clients',
          items: [],
        },
      ],
    },
    {
      texto: 'Impuestos',
      enlace: '/taxlist',
      items: [],
    },
  ];

  toggleMenu() {
    this.menu.nativeElement.classList.toggle('show');
  }
}
