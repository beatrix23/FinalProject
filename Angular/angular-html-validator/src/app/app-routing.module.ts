import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { ActivateComponent } from './components/activate/activate.component';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { ValidatorComponent } from './components/validator/validator.component';
const routes: Routes = [
  {
    path: '',
    component: ValidatorComponent,
  },
  {
    path:'login',
    component: LoginComponent,
  },
  {
    path:'signup',
    component: SignupComponent,
  },
  {
    path:'activate',
    component: ActivateComponent
  },
  {
    path: 'validate',
    component: ValidatorComponent,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
