import { Component, OnInit } from '@angular/core';
import {AdminApplicationStatusService} from "./admin-application-status.service";
import { ApplicationStatusService } from './application-status.service';

@Component({
  selector: 'app-admin-application-status',
  templateUrl: './admin-application-status.component.html',
  styleUrls: ['./admin-application-status.component.css']
})
export class AdminApplicationStatusComponent implements OnInit {


  public status:string;

  constructor(private adminApplicationStatusService:AdminApplicationStatusService, private statusService: ApplicationStatusService) {
  }

  ngOnInit() {
    this.getStatus()
  }

  public changeStatus(status: string) {

    this.adminApplicationStatusService.changeStatus(status);
    this.setStatus(status);
    this.status = status;
  }

  public setStatus(status: string) {
    if (status === "closed") {
      this.statusService.setClosed(true);
      this.statusService.setSandBox(false);
      this.statusService.setProduction(false);
    } else if ( status === "sandbox") {
      this.statusService.setClosed(false);
      this.statusService.setSandBox(true);
      this.statusService.setProduction(false);
    } else if( status === "production") {
      this.statusService.setClosed(false);
      this.statusService.setSandBox(false);
      this.statusService.setProduction(true);
    }
  }

  public getStatus() {
    this.adminApplicationStatusService.getStatus().subscribe(data => {
      this.status = data;
      return data;
    });

    // this.status = this.adminApplicationStatusService.getStatus();
  }



}
