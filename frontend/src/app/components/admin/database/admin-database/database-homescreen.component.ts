import { Component, OnInit } from '@angular/core';
import {AdminDatabaseService} from "./admin-database.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-admin-database',
  templateUrl: './database-homescreen.component.html',
  styleUrls: ['./database-homescreen.component.css']
})
export class DatabaseHomescreenComponent implements OnInit {


  constructor(private service: AdminDatabaseService,private toastrService: ToastrService ) { }

  ngOnInit() {
  }

  makeBackup() {
    this.service.makeBackup();

  }

  restoreBackup() {
    this.service.restoreBackup();
  }

  resetDatabase() {
    let wiping = prompt("Ik wil alles verwijderen");
    if (wiping != "Ik wil alles verwijderen") {
      this.toastrService.warning("Er wordt niks verwijderd");
    } else {
      this.toastrService.info("De database wordt nu leeg gemaakt, Dit account zal verwijderd worden.");
      this.service.wipeAll();
    }
  }

  exportGrades() {
    this.service.exportGrades();

  }

  exitSystem() {
   this.service.exitSystem();
  }





}
