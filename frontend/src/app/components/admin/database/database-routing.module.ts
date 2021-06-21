import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { DatabaseComponent } from './database.component';
import { DatabaseHomescreenComponent } from './admin-database/database-homescreen.component';
import { AddDocumentComponent } from '../../document-overview/add-document/add-document.component';

const databaseRoutes: Routes = [
  {
    path: '',
    component: DatabaseComponent,
    children: [
      { path: 'home', component: DatabaseHomescreenComponent },
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'document', component: AddDocumentComponent}
    ]
  },
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(databaseRoutes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class DatabaseRoutingModule { }
