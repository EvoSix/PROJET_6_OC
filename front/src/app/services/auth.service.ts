import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { RegisterRequest } from '../interfaces/registerRequest';
import { Observable } from 'rxjs';
import { LoginRequest } from '../interfaces/LoginRequest';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);
  private apiUrl = environment.baseUrl;

  constructor() {}
  register(registerRequest: RegisterRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}auth/register`, registerRequest);
  }
  login(payload: LoginRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}auth/login`, payload);
  }
}
