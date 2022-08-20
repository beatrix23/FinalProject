import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Validation } from 'src/app/models/validations';
import { TextToValidate } from '../models/text';
@Injectable({
  providedIn: 'root'
})
export class ValidationService {

  constructor(private httpClient: HttpClient) { }

  public validate(validation: Validation) {
    return this.httpClient.post<any>('http://localhost:8080/validate-link', validation);
  }

  public validateText(text: TextToValidate) {
    return this.httpClient.post<any>('http://localhost:8080/validate-text', text);
  }

  public uploadFile(formData: FormData) {
    return this.httpClient.post<any>('http://localhost:8080/validate-file', formData);
  }

  public getHistory(location: string) {
    return this.httpClient.get<any>('http://localhost:8080/validations?location=' + location); 
  }

  public getHistoryForText(text: string) {
    return this.httpClient.get<any>('http://localhost:8080/validations?location=' + text);
  }

  public getHistoryForFile(fileName: string) {
    return this.httpClient.get<any>('http://localhost:8080/validations?location=' + fileName);
    
  }
}
