import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Post } from '../interfaces/Posts';


@Injectable({
  providedIn: 'root'
})
export class PostService {
  private http = inject(HttpClient);
  private apiUrl = environment.baseUrl;

  getAllPosts(order: 'asc' | 'desc' = 'desc'): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.apiUrl}posts?order=${order}`);
  }
}
