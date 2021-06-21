import { Injectable } from '@angular/core';
import { HttpClient} from "@angular/common/http";
import {ToastrService} from "ngx-toastr";

@Injectable({
  providedIn: 'root'
})
export class AdminDatabaseService {

  constructor(private http:HttpClient, private serivce :AdminDatabaseService, private toastrService:ToastrService) { }

  public makeBackup() {
    this.http.post<Response>("api/backup/", null).subscribe(
      data => {
        alert("De back up is gemaakt")
      },
      error => {
        alert("De back up is niet gemaakt")
      }
    );
  }

  public restoreBackup() {
    this.http.post<Response>("api/backup/restore", null).subscribe(
      data =>{
        alert("De back up is teruggezet")
      },
      error => {
        alert("De back up is niet teruggezet")
      }
    );
  }

  public wipeAll() {
    return this.http.post<Response>("api/backup/wipeAll",null).subscribe();
  }

  public exportGrades() {
    return this.http.post<Response>("api/backup/grades", null).subscribe(
      Data => {
        this.toastrService.success("Cijfers zijn geexporteerd")
      },
      Error => {
        this.toastrService.error("Cijfers konden niet geexporteerd worden")
      }
    );
  }

  public exitSystem() {
    this.http.post<Response>("api/backup/exitsystem", null).subscribe(
      data => {
        alert("systeem is afgesloten")
      },
      error => {
        alert("Er is een fout opgetreden bij het afsluiten van het systeem")
      },
      // timeout =>{
      //   alert("Er is een time out opgetreden dit kan gebeuren als er de applicatie afgesloten is")
      // }
    );
  }
}
