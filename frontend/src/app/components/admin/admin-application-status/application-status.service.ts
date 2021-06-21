import { Injectable } from '@angular/core';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class ApplicationStatusService {

  private closed: boolean = false;
  private production: boolean = false;
  private sandbox: boolean = false;

  constructor(private router: Router) { }


  public checkStatusForView(status){
    if (status === "closed") {
     if (this.isClosed()){
       return;
     }
    } else if ( status === "sandbox") {
      if(this.isSandbox()){
        return;
      }
    } else if( status === "production") {
      if(this.isProduction()){
        return;
      }
    }
    this.router.navigate(['home']);
  }

  public isClosed() {
    return this.closed;
  }

  public setClosed(bool: boolean) {
    this.closed = bool;
  }

  public isProduction() {
    return this.production;
  }

  public setProduction(bool: boolean) {
    this.production = bool;
  }

  public isSandbox() {
    return this.sandbox;
  }

  public setSandBox(bool:boolean) {
    this.sandbox = bool;
  }

  public setStatus(status: string) {
    if (status === "closed") {
      this.setClosed(true);
      this.setSandBox(false);
      this.setProduction(false);
    } else if ( status === "sandbox") {
      this.setClosed(false);
      this.setSandBox(true);
      this.setProduction(false);
    } else if( status === "production") {
      this.setClosed(false);
      this.setSandBox(false);
      this.setProduction(true);
    }
  }
}
