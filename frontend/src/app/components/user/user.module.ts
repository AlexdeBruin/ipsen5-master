import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeScreenComponent } from './home-screen/home-screen.component';
import { SharedModule } from '../shared/shared.module';
import { ProgressOverviewComponent } from './progress-overview/progress-overview.component';


@NgModule({
  imports: [
    CommonModule,
    SharedModule
  ],
  declarations: [HomeScreenComponent]
})
export class UserModule { }
