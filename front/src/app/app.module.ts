import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { provideHttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@NgModule({
  imports: [
    BrowserModule,

    BrowserAnimationsModule,
    MatButtonModule,
    CommonModule,
  ],
  providers: [provideHttpClient()],
})
export class AppModule {}
