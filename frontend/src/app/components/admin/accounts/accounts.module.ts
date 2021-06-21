import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateAccountComponent } from './create-account/create-account.component';
import { BulkAccountCreationComponent } from './bulk-account-creation/bulk-account-creation.component';
import { YesNoPipe } from './yes-no.pipe';
import { MembersPipe } from './members.pipe';
import { AccountRolePipe } from './account-role.pipe';
import { AccountsRoutingModule } from './accounts-routing.module';
import { AccountOverviewComponent } from './account-overview/account-overview.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AccountDetailsComponent } from './account-details/account-details.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ChangeRoleComponent } from './change-role/change-role.component';
import { ChangeNameComponent } from './change-name/change-name.component';

@NgModule({
  imports: [
    CommonModule,
    AccountsRoutingModule,
    ReactiveFormsModule
  ],
  declarations: [
    AccountOverviewComponent,
    CreateAccountComponent,
    BulkAccountCreationComponent,
    YesNoPipe,
    MembersPipe,
    AccountRolePipe,
    AccountDetailsComponent,
    ChangePasswordComponent,
    ChangeRoleComponent,
    ChangeNameComponent,
  ],
  exports: [
    YesNoPipe,
    AccountRolePipe,
  ]
})
export class AccountsModule {
}
