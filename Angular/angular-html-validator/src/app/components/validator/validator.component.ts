import { Component, OnInit } from '@angular/core';
import { Validation } from 'src/app/models/validations';
import { ValidationService } from 'src/app/services/validation.service';
import { ValidationResponse } from 'src/app/models/response';
import { Message } from 'src/app/models/message';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DiffContent, DiffResults } from 'ngx-text-diff/lib/ngx-text-diff.model';

@Component({
	selector: 'app-validator',
	templateUrl: './validator.component.html',
	styleUrls: ['./validator.component.scss'],
})
export class ValidatorComponent implements OnInit {
	displayedColumns: string[] = ['message', 'location'];
	validation: Validation = { entireWebsite: false, href: '' };
	constructor(
		private validationService: ValidationService,
		private snackBar: MatSnackBar,
	) {}

	ngOnInit(): void {}
	response: ValidationResponse[] = [];
	messages: Message[] = [];
	left = '';
	right = '';
	showValidation = true;
	showHistory = false;
	showSpinner = false;

	validate() {
		this.showSpinner = true;
		this.response = [];
		var regex = new RegExp(
			'(\b(https?|ftp|file)://)?[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]',
		);
		if (regex.test(this.validation.href)) {
			this.response = [];
			this.showSpinner = true;
			console.log(this.validation);
			this.validationService.validate(this.validation).subscribe(
				(response: ValidationResponse[]) => {
					if(response) {
						this.showSpinner = false;
					}
					this.response = response;
					this.showValidation = true;
					this.showHistory = false;
				},
				(error) => {
					console.log(error);
					this.response = [];
					this.showValidation = false;
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
		this.showSpinner = true;
		this.validationService.getHistory(href).subscribe(
			(response: ValidationResponse[]) => {
				if(response) {
					this.showSpinner = false;
				}
				this.showValidation = false;
				this.showHistory = true;
				this.left = '';
				for (let i = 0; i < response[1].messages.length; i++) {
					this.left += response[1].messages[i].message + '\n';
					this.left += response[1].messages[i].location + '\n';
					this.left += '\n';
				}
				this.right = '';
				for (let i = 0; i < response[0].messages.length; i++) {
					this.right += response[0].messages[i].message + '\n';
					this.right += response[0].messages[i].location + '\n';
					this.right += '\n';
				}
				if (this.response.length === 0) {
					this.snackBar.open('No history found', '', {
						duration: 2000,
						verticalPosition: 'top',
						horizontalPosition: 'center',
					});
					this.showHistory = false;
					this.showValidation = false;
				}
			},
			(error) => {
				console.log(error);
			},
		);
	}

	onCompareResults(diffResults: DiffResults) {
		console.log('diffResults', diffResults);
	}
}
