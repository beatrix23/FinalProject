import { Component, OnInit, ViewChild } from '@angular/core';
import { Validation } from 'src/app/models/validations';
import { ValidationService } from 'src/app/services/validation.service';
import { ValidationResponse } from 'src/app/models/response';
import { Message } from 'src/app/models/message';
import { TextToValidate } from 'src/app/models/text';
import { MatSnackBar } from '@angular/material/snack-bar';
import {
	DiffContent,
	DiffResults,
} from 'ngx-text-diff/lib/ngx-text-diff.model';

@Component({
	selector: 'app-validator',
	templateUrl: './validator.component.html',
	styleUrls: ['./validator.component.scss'],
})
export class ValidatorComponent implements OnInit {
	displayedColumns: string[] = ['message', 'location'];
	validation: Validation = { entireWebsite: false, location: '' };
	fileValidationResult: ValidationResponse[] = [];
	textValidation: TextToValidate = { text: '' };
	constructor(
		private validationService: ValidationService,
		private snackBar: MatSnackBar,
	) {}

	response: ValidationResponse[] = [];
	messages: Message[] = [];
	left = '';
	right = '';
	showValidation = true;
	showHistory = false;
	showSpinner = false;
	seeComparation = false;
	seeComparationForFile = false;
	seeComparationForText = false;
	fileName = '';
	location = '';
	@ViewChild('myFileInput') myFileInput: any;

	ngOnInit(): void {}

	validate() {
		this.seeComparationForFile = false;
		this.seeComparationForText = false;
		this.seeComparation = true;
		this.showSpinner = true;
		this.response = [];
		var regex = new RegExp(
			'(\b(https?|ftp|file)://)?[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]',
		);
		if (regex.test(this.validation.location)) {
			this.response = [];
			this.showSpinner = true;
			console.log(this.validation);
			this.validationService.validate(this.validation).subscribe(
				(response: ValidationResponse[]) => {
					if (response) {
						this.showSpinner = false;
					}
					if (response[0].messages.length === 0) {
						this.showValidation = false;
						this.snackBar.open('Your code is valid!', '', {
							duration: 6000,
							verticalPosition: 'top',
							horizontalPosition: 'center',
						});
					} else {
						this.response = response;
						this.showValidation = true;
						this.showHistory = false;
						if (localStorage.getItem('token') == null) {
							this.seeComparation = false;
						}
					}
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

	validateText() {
		this.seeComparation = false;
		this.seeComparationForFile = false;
		this.seeComparationForText = true;
		this.validationService
			.validateText(this.textValidation)
			.subscribe((response: ValidationResponse[]) => {
				if (response) {
					this.showSpinner = false;
					this.location = response[0].location;
				}
				if (response[0].messages.length === 0) {
					this.showValidation = false;
					this.snackBar.open('Your code is valid!', '', {
						duration: 6000,
						verticalPosition: 'top',
						horizontalPosition: 'center',
					});
				} else {
					this.response = response;
					this.showValidation = true;
					this.showHistory = false;
					if (localStorage.getItem('token') == null) {
						this.seeComparation = false;
						this.seeComparationForText = false;
					}
				}
			});
	}

	history(location: string) {
		this.showSpinner = true;
		this.validationService.getHistory(location).subscribe(
			(response: ValidationResponse[]) => {
				if (response) {
					this.showSpinner = false;
				}
				this.showValidation = false;
				this.showHistory = true;
				this.left = '';
				if (response.length > 1) {
					for (let i = 0; i < response[1].messages.length; i++) {
						this.left += response[1].messages[i].message + '\n';
						this.left += response[1].messages[i].location + '\n';
						this.left += '\n';
					}
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
				this.snackBar.open('No previous version was found', '', {
					duration: 2000,
					verticalPosition: 'top',
					horizontalPosition: 'center',
				});
			},
		);
	}

	historyLocation() {
		this.showSpinner = true;
		this.validationService.getHistoryForText(this.location).subscribe(
			(response: ValidationResponse[]) => {
				if (response) {
					this.showSpinner = false;
				}
				this.showValidation = false;
				this.showHistory = true;
				this.left = '';
				if (response.length > 1) {
					for (let i = 0; i < response[1].messages.length; i++) {
						this.left += response[1].messages[i].message + '\n';
						this.left += response[1].messages[i].location + '\n';
						this.left += '\n';
					}
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
				}
			},
			(error) => {
				console.log(error);
				this.snackBar.open('No previous version was found', '', {
					duration: 2000,
					verticalPosition: 'top',
					horizontalPosition: 'center',
				});
			},
		);
	}

	onFileSelected(event: Event) {
		var file: File = (event!.target! as HTMLInputElement).files![0];
		
		if (file) {
			this.fileName = file.name;
			const formData = new FormData();
			formData.append('file', file);
			this.validationService.uploadFile(formData).subscribe(
				(response: ValidationResponse[]) => {
					if (response) {
						this.showSpinner = false;
						this.location = response[0].location;
					}
					if (response[0].messages.length === 0) {
						this.showValidation = false;
						this.snackBar.open('Your code is valid!', '', {
							duration: 6000,
							verticalPosition: 'top',
							horizontalPosition: 'center',
						});
					} else {
						this.fileValidationResult = response;
						if (localStorage.getItem('token') == null) {
							this.seeComparation = false;
							this.seeComparationForFile = false;
						}
					}
				});
		}
	}

	showFileValidation() {
		this.seeComparationForFile = true;
		this.seeComparationForText = false;
		this.location = this.fileValidationResult[0].location;
		this.seeComparation = false;
		this.showValidation = true;
		this.showHistory = false;
		this.myFileInput.nativeElement.value = '';
		this.response = this.fileValidationResult;
		if (localStorage.getItem('token') == null) {
			this.seeComparation = false;
			this.seeComparationForFile = false;
		}
	}

	onCompareResults(diffResults: DiffResults) {
		console.log('diffResults', diffResults);
	}
}
