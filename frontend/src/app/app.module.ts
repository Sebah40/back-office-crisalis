import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UsersModule } from './users/users.module';
import { HttpClientModule } from '@angular/common/http';
import { UsersRoutingModule } from './users/users.routing.module';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    UsersModule,
    HttpClientModule,
    UsersRoutingModule,
    RouterModule,
  ],

  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
