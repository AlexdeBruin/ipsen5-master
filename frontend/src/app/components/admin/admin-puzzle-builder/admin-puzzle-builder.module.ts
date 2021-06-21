import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PuzzleBuildingRoutingModule } from './puzzle-builder-routing.module';
import { AdminPuzzleBuilderScreenComponent } from './admin-puzzle-builder-screen/admin-puzzle-builder-screen.component';
import { PuzzleService } from '../../puzzle/puzzle-builder-screen/puzzle.service';
import { FormsModule } from '@angular/forms';
import { CostCardComponent } from '../../puzzle/cost-card/cost-card.component';
import { PuzzleModule } from '../../puzzle/puzzle.module';

@NgModule({
  imports: [
    CommonModule,
    PuzzleBuildingRoutingModule,
    FormsModule,
    PuzzleModule
  ],
  declarations: [AdminPuzzleBuilderScreenComponent],
  providers: [PuzzleService]
})
export class AdminPuzzleBuilderModule { }
