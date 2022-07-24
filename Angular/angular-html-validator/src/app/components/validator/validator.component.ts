import { Component, OnInit } from '@angular/core';
import { Validation } from 'src/app/models/validations';
import { ValidationService } from 'src/app/services/validation.service';
import { ValidationResponse } from 'src/app/models/response';
import { Message } from 'src/app/models/message';
@Component({
  selector: 'app-validator',
  templateUrl: './validator.component.html',
  styleUrls: ['./validator.component.scss']
})
export class ValidatorComponent implements OnInit {
  displayedColumns: string[] = ['message', 'location'];
  validation: Validation = { entireWebsite: false, href: '' };
  display: boolean = false;
  constructor(private validationService: ValidationService) { }

  ngOnInit(): void {

  }
 response : ValidationResponse[] = [];
 messages: Message[] = [];
 validate() {
  console.log(this.validation);
    this.validationService.validate(this.validation).subscribe((response: ValidationResponse[]) => {
      this.response = response;
      this.display = true;
    }, (error) => {
      console.log(error);
    }
    );
  }

  history(href: string) {
    this.validationService.getHistory(href).subscribe((response: ValidationResponse[]) => {
      this.response = response;
      this.display = true;
    }
    , (error) => {
      console.log(error);
    }
    );
  }
}
