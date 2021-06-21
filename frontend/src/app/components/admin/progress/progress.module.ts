import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProgressRoutingModule} from "./progress-routing.module";
import {ProgressService} from "./progress.service";
import {ProgressComponent} from "./progress.component";
import {AccountsModule} from "../accounts/accounts.module";

@NgModule({
  imports: [
    CommonModule,
    ProgressRoutingModule,
    AccountsModule
  ],
  providers: [
    ProgressService
  ],
  declarations: [
    ProgressComponent,
  ]
})
export class ProgressModule { }
