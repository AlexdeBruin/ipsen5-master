import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DatabaseComponent } from './database.component';
import { DatabaseHomescreenComponent } from './admin-database/database-homescreen.component';
import { DatabaseRoutingModule } from './database-routing.module';
import { DocumentOverviewModule } from '../../document-overview/document-overview.module';

@NgModule({
  imports: [
    CommonModule,
    DatabaseRoutingModule,
    DocumentOverviewModule
  ],
  declarations: [
    DatabaseComponent,
    DatabaseHomescreenComponent
  ]
})
export class DatabaseModule { }
