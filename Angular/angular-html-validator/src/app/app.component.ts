import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'angular-html-validator';
  showButton = false;

  constructor(private _snackBar: MatSnackBar, private router:Router) { }
  
  ngOnInit(): void {
    this.checkLogin();
    };

    checkLogin (){
      console.log('Token is: ', localStorage.getItem("token"))
      if (localStorage.getItem("token") != null){
        this.showButton = true;
      } else {
        this.showButton = false;
        this._snackBar.open("If you want to be able to compare you last validations please log in/register", "Ok", {
          duration: 10000*10000,
          verticalPosition: 'top',
          horizontalPosition: 'center',
        });
      }
    }

  onLogout(){
    localStorage.removeItem("token");
    this.checkLogin();
    this.router.navigate(['/login']);
    this._snackBar.open("You are now logged out. ", "Ok", {
      verticalPosition: 'top',
      duration: 6 * 1000,
    });
  }
}
