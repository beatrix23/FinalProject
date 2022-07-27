import { Component, OnInit } from '@angular/core';
import { Validation } from 'src/app/models/validations';
import { ValidationService } from 'src/app/services/validation.service';
import { ValidationResponse } from 'src/app/models/response';
import { Message } from 'src/app/models/message';
import { MatSnackBar } from '@angular/material/snack-bar';
@Component({
	selector: 'app-validator',
	templateUrl: './validator.component.html',
	styleUrls: ['./validator.component.scss'],
})
export class ValidatorComponent implements OnInit {
	displayedColumns: string[] = ['message', 'location'];
	validation: Validation = { entireWebsite: false, href: '' };
	display: boolean = false;
	constructor(
		private validationService: ValidationService,
		private snackBar: MatSnackBar,
	) {}

	ngOnInit(): void {}
	response: ValidationResponse[] = [];
	messages: Message[] = [];
	validate() {
		var regex = new RegExp(
			'(\b(https?|ftp|file)://)?[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]',
		);
		if (regex.test(this.validation.href)) {
			console.log(this.validation);
			this.validationService.validate(this.validation).subscribe(
				(response: ValidationResponse[]) => {
					this.response = response;
					this.display = true;
				},
				(error) => {
          console.log(error);
          this.response =[];
          this.display = false;
          this.snackBar.open('Please enter a valid URL', '', {
            duration: 2000,
            verticalPosition: 'top',
            horizontalPosition: 'center',
          });
				},
			);
		}
	}

	history(href: string) {
		this.validationService.getHistory(href).subscribe(
			(response: ValidationResponse[]) => {
				this.response = response;
				this.display = true;
        if(this.response.length === 0) {
          this.snackBar.open('No history found', '', {
            duration: 2000,
            verticalPosition: 'top',
            horizontalPosition: 'center',
          });
          this.display = false;
        }
			},
			(error) => {
				console.log(error);
			},
		);
	}
}
