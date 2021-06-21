import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import {ProgressComponent} from "./progress.component";

const progressRoutes: Routes = [
  {
    path: '',
    component: ProgressComponent,
    children: [
      { path: 'home', component: ProgressComponent },
      { path: '', redirectTo: 'home', pathMatch: 'full' }
    ]
  },
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(progressRoutes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class ProgressRoutingModule { }
