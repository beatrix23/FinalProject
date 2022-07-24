import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserInfo } from 'src/app/models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }
  
  public signup(userInfo: UserInfo) {
    return this.httpClient.post<any>('http://localhost:8080/users/signup', userInfo);
  }

  public login(userInfo: UserInfo) {
    return this.httpClient.post<any>('http://localhost:8080/users/login', userInfo);
  }
}
