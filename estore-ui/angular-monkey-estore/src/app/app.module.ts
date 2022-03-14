import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { ProductPageComponent } from './product-page/product-page.component';
import { MonkeySearchComponent } from './monkey-search/monkey-search.component';
import { BuyerProductListComponent } from './buyer-product-list/buyer-product-list.component';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';

@NgModule({
  declarations: [
    AppComponent,
    ProductPageComponent,
    MonkeySearchComponent,
    BuyerProductListComponent,
    ShoppingCartComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
