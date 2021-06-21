import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './footer/footer.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { NotfoundComponent } from './notfound/notfound.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule
  ],
  declarations: [FooterComponent, NavBarComponent, NotfoundComponent],
  exports: [FooterComponent, NavBarComponent, RouterModule]
})
export class SharedModule { }
