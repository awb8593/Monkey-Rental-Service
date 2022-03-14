import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { BuyerProductListComponent } from './buyer-product-list/buyer-product-list.component';

const routes: Routes = [
  { path: 'buyer-product-list', component: BuyerProductListComponent }
];


@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
