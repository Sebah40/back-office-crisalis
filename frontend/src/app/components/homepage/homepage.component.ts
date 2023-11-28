import { Component } from '@angular/core';
import { Section } from './Section';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css'],
})
export class HomepageComponent {
  sectionCustomer: Section = {
    imagen: '../assets/png/clients2.png',
    color: '#77AFA9',
    nombre: 'Lista de personas',
    path: '/person/',
  };
  sectionEnterprise: Section = {
    imagen: '../assets/png/enterprise.png',
    color: '#C8A34E',
    nombre: 'Lista de empresas',
    path: '/enterprise/',
  };
  sectionOrders: Section = {
    imagen: '../assets/png/order2.png',
    color: '#46A17A',
    nombre: 'Lista de pedidos',
    path: '/order/getAll',
  };
  sectionProducts: Section = {
    imagen: '../assets/png/service4.png',
    color: '#C8A34E',
    nombre: 'Lista de productos y servicios',
    path: '/good',
  };
  sectionChart: Section = {
    imagen: '../assets/png/informes1.png',
    color: '#C8A34E',
    nombre: 'Informes',
    path: '/report',
  };
}
