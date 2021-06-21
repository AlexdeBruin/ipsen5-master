import {
  HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest,
  HttpResponse
} from "@angular/common/http";
import {TokenService} from "./token.service";
import {Router} from "@angular/router";
import { Observable } from "rxjs";
import {isUndefined} from "util";
import {Injectable} from "@angular/core";
import 'rxjs/add/operator/do';


@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(private router: Router, public tokenservice: TokenService){}
  intercept(request: HttpRequest<any>, next: HttpHandler):Observable<HttpEvent<any>>{

    request = request.clone({
      setHeaders:{
        Authorization:  `Bearer ${this.tokenservice.getToken()}`
    }
    });
    return next.handle(request).do((event: HttpEvent<any>) => {
      if(event instanceof  HttpResponse){
        //this.tokenservice.setIsLoggedIn(true);
      }
    }, (error: any )=>{
      if(error instanceof HttpErrorResponse){
        if(error.status == 403){
          this.tokenservice.setIsLoggedIn(false);
        }
      }
      }
      )
  }
}
