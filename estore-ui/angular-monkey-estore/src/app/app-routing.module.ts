import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OwnerPageComponent } from './owner-page/owner-page.component';

const routes: Routes = [
  { path: 'owner-page', component: OwnerPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
