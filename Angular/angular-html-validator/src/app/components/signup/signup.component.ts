import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { JwtHelperService } from '@auth0/angular-jwt';
import { UserInfo } from 'src/app/models/user';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {
  hide = true;
  signUp: UserInfo = { username: '', password: '' };
  helper = new JwtHelperService();
  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private userService: UserService,
    private snackBar: MatSnackBar
  ) { }
  signupForm!: FormGroup;
  ngOnInit(): void {
    this.signupForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  getUsername() {
    return this.signupForm.get('username');
  }

  getPassword() { 
    return this.signupForm.get('password');
  }

  signup() {
    this.userService.signup(this.signupForm.value).subscribe((tokenObject) => {
      localStorage.setItem('token', tokenObject.token);
      this.snackBar.open('Signup successful', '', {
        duration: 2000,
        verticalPosition: 'top',
        horizontalPosition: 'center'
      });
      location.href = '/validate';
    }, (error) => {
      this.snackBar.open('Signup failed', '', {
        duration: 2000,
        verticalPosition: 'top',
        horizontalPosition: 'center'
      });
    })
  }
}
