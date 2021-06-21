import { Component, OnInit } from '@angular/core';
import { PuzzleService } from '../puzzle-builder-screen/puzzle.service';
import {ApplicationStatusService} from "../../admin/admin-application-status/application-status.service";

@Component({
  selector: 'app-sandbox-list',
  templateUrl: './sandbox-list.component.html',
  styleUrls: ['./sandbox-list.component.css']
})
export class SandboxListComponent implements OnInit {

  idslist : Array<Number>;

  constructor(private puzzleService : PuzzleService, private applicationStatusService:ApplicationStatusService ) { }

  ngOnInit() {

    this.applicationStatusService.checkStatusForView("sandbox");

    this.puzzleService.getSandboxList().subscribe(
      data => {
        this.idslist = data;
      }
    )
  }

  public isSmallerThan10(subject : Number) : boolean {
    return subject < 10
  }

  public hasdata() {
    return this.idslist != null;
  }
}
