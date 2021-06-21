import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { PuzzleBuilderScreenComponent } from './puzzle-builder-screen/puzzle-builder-screen.component';
import { PuzzleListComponent } from './puzzle-list/puzzle-list.component';
import { SolvePuzzleComponent } from './solve-puzzle/solve-puzzle.component';
import { CostCardComponent } from './cost-card/cost-card.component';
import { FormsModule, ReactiveFormsModule} from "@angular/forms";
import {PuzzleService} from "./puzzle-builder-screen/puzzle.service";
import { InterpreterComponent } from './interpreter/interpreter.component';
import { SolveSandboxPuzzleComponent } from './solve-sandbox-puzzle/solve-sandbox-puzzle.component';
import { SandboxListComponent } from './sandbox-list/sandbox-list.component';
import { AceEditorModule} from "ng2-ace-editor";
import {ProgressOverviewComponent} from "../user/progress-overview/progress-overview.component";

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    FormsModule,
    AceEditorModule
  ],
  declarations: [PuzzleBuilderScreenComponent,ProgressOverviewComponent, PuzzleListComponent, SolvePuzzleComponent, CostCardComponent, InterpreterComponent, SolveSandboxPuzzleComponent, SandboxListComponent],
  providers:[PuzzleService,
    ],
  exports: [CostCardComponent, ProgressOverviewComponent]
})
export class PuzzleModule { }
