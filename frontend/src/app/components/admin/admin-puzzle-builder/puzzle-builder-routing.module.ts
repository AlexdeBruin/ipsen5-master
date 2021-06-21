import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { AdminPuzzleBuilderScreenComponent } from './admin-puzzle-builder-screen/admin-puzzle-builder-screen.component';

const adminPuzzelBuilderRoutes: Routes = [
  {
    path: '',
    component: AdminPuzzleBuilderScreenComponent,
    children: []
  },
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(adminPuzzelBuilderRoutes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class PuzzleBuildingRoutingModule { }
