import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomepageComponent } from './homepage/homepage.component';
import { HomeSectionsComponent } from './home-sections/home-sections.component';
import { UpperbarComponent } from './upperbar/upperbar.component';

import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {path: '', component: HomepageComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    HomeSectionsComponent,
    UpperbarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(routes),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
