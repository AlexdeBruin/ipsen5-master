import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginScreenComponent } from './login-screen/login-screen.component';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';
import {FormsModule} from "@angular/forms";
import { PasswordChangeComponent } from './password-change/password-change.component';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    FormsModule
  ],
  declarations: [LoginScreenComponent, PasswordChangeComponent]
})
export class LoginModule { }
