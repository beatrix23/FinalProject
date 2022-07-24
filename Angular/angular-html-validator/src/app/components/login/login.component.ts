import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { JwtHelperService } from '@auth0/angular-jwt';
import { UserInfo } from 'src/app/models/user';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  hide = true;
  logIn: UserInfo = { username: '', password: '' };
  helper = new JwtHelperService();
  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private userService: UserService,
    private snackBar: MatSnackBar
  ) { }
loginForm!: FormGroup;
  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    }); 
  }

  getUsername() {
    return this.loginForm.get('username');
  }

  getPassword() {
    return this.loginForm.get('password');
  }

  login() {
    this.userService.login(this.loginForm.value).subscribe((tokenObject) => {
      localStorage.setItem('token', tokenObject.token);
      this.router.navigate(['/validate']);
      this.snackBar.open('Login successful', '', {
        duration: 2000,
        verticalPosition: 'top',
        horizontalPosition: 'center'
      });
      this.loginForm.reset();
    }, (error) => {
      this.snackBar.open('Login failed', '', {
        duration: 2000,
        verticalPosition: 'top',
        horizontalPosition: 'center'
      });
    })
  }
}
