import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { LoginScreenComponent } from './components/login/login-screen/login-screen.component';
import { HomeScreenComponent } from './components/user/home-screen/home-screen.component';
import { RouterModule, Routes } from '@angular/router';
import { PuzzleBuilderScreenComponent } from './components/puzzle/puzzle-builder-screen/puzzle-builder-screen.component';
import { DocumentOverviewScreenComponent } from './components/document-overview/document-overview-screen/document-overview-screen.component';
import { DocumentOverviewModule } from './components/document-overview/document-overview.module';
import { LoginModule } from './components/login/login.module';
import { SharedModule } from './components/shared/shared.module';
import { UserModule } from './components/user/user.module';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { LoginService } from './components/login/login.service';
import { TokenService } from './components/login/token/token.service';
import { AuthGuardService } from './components/login/authGuard/auth-guard.service';
import { PasswordChangeService } from './components/login/authGuard/password-change.service';
import { TokenInterceptor } from './components/login/token/token.interceptor';
import { PuzzleModule } from './components/puzzle/puzzle.module';
import { PasswordChangeComponent } from './components/login/password-change/password-change.component';
import { PuzzleService } from './components/puzzle/puzzle-builder-screen/puzzle.service';
import { MatDialogModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PuzzleListComponent } from './components/puzzle/puzzle-list/puzzle-list.component';
import { SolvePuzzleComponent } from './components/puzzle/solve-puzzle/solve-puzzle.component';
import { AceEditorModule } from 'ng2-ace-editor';
import { HighscoreComponent } from './components/highscore/highscore/highscore.component';
import { NotfoundComponent } from './components/shared/notfound/notfound.component';
import { SolveSandboxPuzzleComponent } from './components/puzzle/solve-sandbox-puzzle/solve-sandbox-puzzle.component';
import { SandboxListComponent } from './components/puzzle/sandbox-list/sandbox-list.component';
import { ToastrModule } from 'ngx-toastr';
import { AdminGuard } from './components/login/admin.guard';
import { ApplicationStatusService } from './components/admin/admin-application-status/application-status.service'
import {ProgressOverviewComponent} from "./components/user/progress-overview/progress-overview.component";
import { StudentManualComponent } from './components/student-manual/student-manual.component';


const appRoutes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginScreenComponent },
  { path: 'home', component: HomeScreenComponent, canActivate: [AuthGuardService] },
  { path: 'puzzleBuilder', component: PuzzleBuilderScreenComponent, canActivate: [AuthGuardService] },
  { path: 'documentoverview', component: DocumentOverviewScreenComponent, canActivate: [AuthGuardService] },
  { path: 'puzzlelist', component: PuzzleListComponent, canActivate: [AuthGuardService] },
  { path: 'puzzlesolver', component: SolvePuzzleComponent, canActivate: [AuthGuardService] },
  { path: 'sandboxlist', component: SandboxListComponent, canActivate: [AuthGuardService] },
  { path: 'sandboxsolver', component: SolveSandboxPuzzleComponent, canActivate: [AuthGuardService] },
  { path: 'highscore', component: HighscoreComponent, canActivate: [AuthGuardService] },
  { path: 'manual', component: StudentManualComponent, canActivate: [AuthGuardService] },
  {
    path: 'admin',
    loadChildren: 'app/components/admin/admin.module#AdminModule',
    canActivate: [AdminGuard]
  },
  { path: 'forcepassword', component: PasswordChangeComponent, canActivate: [PasswordChangeService] },
  { path: 'notfound', component: NotfoundComponent },
  { path: '**', redirectTo: 'notfound' },

];


@NgModule({
  declarations: [
    AppComponent,
    HighscoreComponent,
    StudentManualComponent,
  ],
  imports: [
    MatDialogModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(appRoutes),
    BrowserModule,
    DocumentOverviewModule,
    LoginModule,
    SharedModule,
    UserModule,
    PuzzleModule,
    HttpClientModule,
    AceEditorModule,
    ToastrModule.forRoot({
      positionClass: 'toast-bottom-left',
      maxOpened: 5,
      autoDismiss: true
      }
    )
  ],
  providers: [
    LoginService,
    TokenService,
    AuthGuardService,
    AdminGuard,
    PasswordChangeService,
    PuzzleService, {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    ApplicationStatusService
  ],
  bootstrap: [AppComponent],
  exports: [BrowserModule]
})
export class AppModule {
}
