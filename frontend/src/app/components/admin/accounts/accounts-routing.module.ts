import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateAccountComponent } from './create-account/create-account.component';
import { BulkAccountCreationComponent } from './bulk-account-creation/bulk-account-creation.component';
import { AccountOverviewComponent } from './account-overview/account-overview.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ChangeRoleComponent } from './change-role/change-role.component';
import { ChangeNameComponent } from './change-name/change-name.component';

const accountRoutes: Routes = [
  { path: 'create-account', component: CreateAccountComponent },
  { path: 'create-accounts-in-bulk', component: BulkAccountCreationComponent },
  { path: 'overview', component: AccountOverviewComponent },
  { path: 'changePassword/:name', component: ChangePasswordComponent },
  { path: 'changeRole/:name', component: ChangeRoleComponent },
  { path: 'changeName/:name', component: ChangeNameComponent },
  { path: '', redirectTo: 'overview', pathMatch: 'full' }
];


@NgModule({
  imports: [
    RouterModule.forChild(accountRoutes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AccountsRoutingModule {
}
