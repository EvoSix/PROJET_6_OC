import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RegisterRequest } from '../interfaces/registerRequest';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  // Adapter si le préfixe est différent

  constructor(private http: HttpClient) {}
  private apiUrl = environment.baseUrl;
  getCurrentUser(): Observable<RegisterRequest> {
    return this.http.get<RegisterRequest>(`${this.apiUrl}users/me`);
  }

  updateUser(data: RegisterRequest): Observable<RegisterRequest> {
    return this.http.put<RegisterRequest>(`${this.apiUrl}users/me`, data);
  }
}
