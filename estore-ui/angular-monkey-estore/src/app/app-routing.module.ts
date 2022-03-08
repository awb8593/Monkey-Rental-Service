import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { BuyerProductListComponent } from './buyer-product-list/buyer-product-list.component';
import {ProductPageComponent } from './product-page/product-page.component';

const routes : Routes = [
  { path: 'buyer-product-list', component: BuyerProductListComponent },
  { path: 'product-page', component: ProductPageComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
