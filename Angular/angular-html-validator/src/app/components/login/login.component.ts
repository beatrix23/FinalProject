import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { JwtHelperService } from '@auth0/angular-jwt';
import { UserInfo } from 'src/app/models/user';
import { AppComponent } from 'src/app/app.component';
@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
	hide = true;
	logIn: UserInfo = { email: '', password: '' };
	helper = new JwtHelperService();
	constructor(
		private formBuilder: FormBuilder,
		private router: Router,
		private userService: UserService,
		private snackBar: MatSnackBar,
	) {}
	loginForm!: FormGroup;
	ngOnInit(): void {
		this.loginForm = this.formBuilder.group({
			email: ['', Validators.required],
			password: ['', Validators.required],
		});
	}

	getEmail() {
		return this.loginForm.get('email');
	}

	getPassword() {
		return this.loginForm.get('password');
	}

	login() {
		this.userService.login(this.loginForm.value).subscribe(
			(tokenObject) => {
				localStorage.setItem('token', tokenObject.token);
				this.snackBar.open('Login successful', '', {
					duration: 2000,
					verticalPosition: 'top',
					horizontalPosition: 'center',
				});
        location.href = '/validate';
			},
			(error) => {
				this.snackBar.open('Invalid username or password', '', {
					duration: 2000,
					verticalPosition: 'top',
					horizontalPosition: 'center',
				});
			},
		);
	}
}
