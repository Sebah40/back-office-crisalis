import { Component, ElementRef, ViewChild } from '@angular/core';
import { Item } from '../menu-item/item';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent {

  @ViewChild('menu') menu!: ElementRef;

  home:Item = {
    texto: "Home",
    enlace: "/home",
    items: []
  };
  
  items:Item[] = [ 
    {
      texto:"Usuarios",
      enlace: "/user",
      items: [
        // {
        //   texto: "Crear",
        //   enlace: "",
        //   items: []
        // },
        // {
        //   texto: "Modificar",
        //   enlace: "#",
        //   items: []
        // },
        // {
        //   texto: "Eliminar",
        //   enlace: "#",
        //   items: []
        // }
      ]
    },
    {
      texto:"Productos",
      enlace: "#",
      items: [
        {
          texto: "Crear",
          enlace: "#",
          items: []
        },
        {
          texto: "Modificar",
          enlace: "#",
          items: []
        },
        {
          texto: "Eliminar",
          enlace: "#",
          items: []
        }
      ]
    },
    {
      texto:"Pedidos",
      enlace: "#",
      items: [
        {
          texto: "Crear",
          enlace: "#",
          items: []
        },
        {
          texto: "Modificar",
          enlace: "#",
          items: []
        },
        {
          texto: "Eliminar",
          enlace: "#",
          items: []
        }
      ]
    },
    {
      texto:"Impuestos",
      enlace: "#",
      items: [
        {
          texto: "Crear",
          enlace: "#",
          items: []
        },
        {
          texto: "Modificar",
          enlace: "#",
          items: []
        },
        {
          texto: "Eliminar",
          enlace: "#",
          items: []
        }
      ]
    },
    {
      texto:"Informes",
      enlace: "#",
      items: []
    }
  ]

  toggleMenu(){
    this.menu.nativeElement.classList.toggle("show");
  }
}
