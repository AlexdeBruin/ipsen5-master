import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminHomeScreenComponent } from './admin-home-screen/admin-home-screen.component';
import { AdminComponent } from './admin/admin.component';
import { AdminApplicationStatusComponent } from './admin-application-status/admin-application-status.component';

const adminRoutes: Routes = [
  {
    path: '', component: AdminComponent,
    children: [
      { path: 'home', component: AdminHomeScreenComponent },
      {
        path: 'accounts',
        loadChildren: 'app/components/admin/accounts/accounts.module#AccountsModule'
      },
      {
        path: 'database',
        loadChildren: 'app/components/admin/database/database.module#DatabaseModule'
      },
      {
        path: 'omgevingStatus', component: AdminApplicationStatusComponent,
      },
      {
        path: 'puzzlebuilder',
        loadChildren: 'app/components/admin/admin-puzzle-builder/admin-puzzle-builder.module#AdminPuzzleBuilderModule'
      },
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      {
        path: 'progress',
        loadChildren: 'app/components/admin/progress/progress.module#ProgressModule'
      },
    ]
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(adminRoutes)
  ],
  declarations: [],
  exports: [RouterModule]
})
export class AdminRoutingModule {
}
