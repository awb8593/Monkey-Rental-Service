import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { OwnerPageComponent } from './owner-page/owner-page.component';
import { BuyerProductListComponent } from './buyer-product-list/buyer-product-list.component';

const routes: Routes = [
  { path: 'owner-page', component: OwnerPageComponent},
  { path: 'buyer-product-list', component: BuyerProductListComponent }
];


@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
