import { Component } from '@angular/core';
import { Section } from './Section';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent {
  sectionCustomer:Section = {
    imagen: '../assets/sections/objetivo.png',
    color: '#77AFA9',
    nombre: 'Clientes'
  }
  sectionOrders:Section = {
    imagen: '../assets/sections/orden.png',
    color: '#46A17A',
    nombre: 'Pedidos'
  }
  sectionProducts:Section = {
    imagen: '../assets/sections/paquete.png',
    color: '#C8A34E',
    nombre: 'Productos y servicios'
  }
}
