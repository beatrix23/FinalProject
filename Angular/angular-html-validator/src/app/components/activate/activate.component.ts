import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Activation } from 'src/app/models/activation';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-activate',
  templateUrl: './activate.component.html',
  styleUrls: ['./activate.component.scss']
})
export class ActivateComponent implements OnInit {

  constructor(private userService: UserService,
    private snackBar: MatSnackBar,
    private router: Router) { }

  ngOnInit(): void {
  }
  code!: string;
  activation: Activation = { email: '', code: '' };

  activate(code: string) {
    this.code = code;
    this.activation.code = code;
    this.activation.email = localStorage.getItem('email')!;
    this.userService.activate(this.activation).subscribe((tokenObject) => {
      localStorage.setItem('token', tokenObject.token);
      this.snackBar.open('Activation successful', '', {
        duration: 2000,
        verticalPosition: 'top',
        horizontalPosition: 'center'
      });
      this.router.navigate(['/login']);
    }, (error) => {
      this.snackBar.open('Activation failed', '', {
        duration: 2000,
        verticalPosition: 'top',
        horizontalPosition: 'center'
      });
    })
  }
}
