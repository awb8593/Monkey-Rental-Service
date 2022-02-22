import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { MonkeySearchComponent } from './monkey-search/monkey-search.component';

@NgModule({
  declarations: [
    AppComponent,
    MonkeySearchComponent
  ],
  imports: [
    BrowserModule
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
