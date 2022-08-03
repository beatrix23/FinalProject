import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Validation } from 'src/app/models/validations';
@Injectable({
  providedIn: 'root'
})
export class ValidationService {

  constructor(private httpClient: HttpClient) { }

  public validate(validation: Validation) {
    return this.httpClient.post<any>('http://localhost:8080/validate', validation);
  }

  public getHistory(href: string) {
    return this.httpClient.get<any>('http://localhost:8080/validations?href=' + href);
  }
}
