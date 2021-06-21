import { Component, OnInit } from '@angular/core';
import {AdminDatabaseService} from "../database/admin-database/admin-database.service";

@Component({
  selector: 'app-admin-home-screen',
  templateUrl: './admin-home-screen.component.html',
  styleUrls: ['./admin-home-screen.component.css']
})
export class AdminHomeScreenComponent implements OnInit {

  constructor() { }

  ngOnInit() {

  }

  // exitSystem() {
  //   this.serivce.exitSystem();
  // }

}
