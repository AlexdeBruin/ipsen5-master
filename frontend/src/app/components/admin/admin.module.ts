import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminRoutingModule } from './admin-routing.module';
import { AccountService } from './account.service';
import { AdminHomeScreenComponent } from './admin-home-screen/admin-home-screen.component';
import { AdminComponent } from './admin/admin.component';
import { AdminApplicationStatusComponent } from './admin-application-status/admin-application-status.component';
import {AdminApplicationStatusService} from "./admin-application-status/admin-application-status.service";


@NgModule({
  imports: [
    CommonModule,
    AdminRoutingModule,
  ],
  providers: [
    AccountService,
    AdminApplicationStatusService
  ],
  declarations: [
    AdminHomeScreenComponent,
    AdminComponent,
    AdminApplicationStatusComponent,
  ]
})
export class AdminModule {
}
