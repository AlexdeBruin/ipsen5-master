import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ToastrService} from "ngx-toastr";

@Injectable({
  providedIn: 'root'
})
export class AdminApplicationStatusService {

  private status: string;

  constructor(private http : HttpClient, private toastrService:ToastrService) { }

  public changeStatus(status: string) {
    return this.http.post("api/status/change", status).subscribe(
      data => {this.toastrService.success("Status is changed too:" + status)},
      error => {
        this.toastrService.error("Could not change the status")
      }
    )
  }

  public getStatus(){
    return this.http.get("api/status", {responseType: 'text'});
  }

  //alternatief is string terug sturen inplaats van response
  //
  // public getStatus(){
  //     this.Http.get<string>("api/status").subscribe(
  // data => {
  //  this.status = data
  // return data
  // });
  //   }

  // async getStat() {
  //   const stat = await this.Http.get<string>("api/status").toPromise();
  //   this.status = stat;
  //   return stat;
  // }
  //
  // public getStatus() {
  //   this.getStat();
  //   return this.status;
  // }

}
