import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { interceptorProvider } from './modules/auth/service/interceptor.service';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UsersModule } from './modules/user/user.module';
import { UsersRoutingModule } from './modules/user/user.routing.module';
import { HomepageComponent } from './components/homepage/homepage.component';
import { HomeSectionsComponent } from './components/home-sections/home-sections.component';
import { UpperbarComponent } from './components/upperbar/upperbar.component';
import { RouterModule, Routes } from '@angular/router';
import { MenuComponent } from './modules/menu/components/menu/menu.component';
import { MenuItemComponent } from './modules/menu/components/menu-item/menu-item.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './modules/auth/components/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { ClientListComponent } from './modules/client/components/enterprise-list/enterprise-list.component';
import { PersonListComponent } from './modules/client/components/person-list/person-list.component';
import { EnterpriseFormComponent } from './modules/client/components/enterprise-form/enterprise-form.component';
import { PersonFormComponent } from './modules/client/components/person-form/person-form.component';
import { SellableGoodsModule } from './modules/sellable-good/sellable-good.module';
import { TaxListComponent } from './modules/tax/components/tax-list/tax-list.component';
import { TaxCreateComponent } from './modules/tax/components/tax-create/tax-create.component';
import { OrderComponent } from './order/order-view/order.component';
import { OrderListComponent } from './order/order-list/order-list.component';
import { CreateOrderComponent } from './order/create-order/create-order.component';
import { EditOrderComponent } from './order/edit-order/edit-order.component';
import { ClientModule } from './modules/client/client.module';

import { ForgotPasswordModalComponent } from './modules/auth/components/forgot-password-modal/forgot-password-modal.component';

import {
  TranslateLoader,
  TranslateModule,
  TranslateService,
} from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpLoaderFactory } from './helper/httpLoaderFactory';
import { SharedModule } from './modules/shared/shared.module';
import { ReportModule } from './modules/report/report.module';
import { ReportRoutingModule } from './modules/report/report.routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

const routes: Routes = [{ path: '', component: HomepageComponent }];

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    HomeSectionsComponent,
    UpperbarComponent,
    MenuComponent,
    MenuItemComponent,
    LoginComponent,
    PageNotFoundComponent,
    ClientListComponent,
    PersonListComponent,
    EnterpriseFormComponent,
    PersonFormComponent,
    TaxListComponent,
    TaxCreateComponent,
    OrderComponent,
    OrderListComponent,
    CreateOrderComponent,
    EditOrderComponent,
    ForgotPasswordModalComponent,
  ],
  imports: [
    SweetAlert2Module.forRoot(),
    SharedModule,
    BrowserModule,
    UsersModule,
    ReportModule,
    SellableGoodsModule,
    HttpClientModule,
    UsersRoutingModule,
    FormsModule,
    ClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
    RouterModule.forRoot(routes),
    ReactiveFormsModule,
    TranslateModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient],
      },
    }),
    BrowserAnimationsModule,
  ],
  providers: [interceptorProvider, TranslateService],
  bootstrap: [AppComponent],
})
export class AppModule {
  constructor(private translate: TranslateService) {
    translate.setDefaultLang('es');
  }
}
