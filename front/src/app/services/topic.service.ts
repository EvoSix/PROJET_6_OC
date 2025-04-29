import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../interfaces/Topic';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class TopicService {
  private apiUrl = environment.baseUrl;
  constructor(private http: HttpClient) {}
  getTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(`${this.apiUrl}topics`);
  }
  subscribeToTopic(topicId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}topics/${topicId}/subscribe`, {}); // Envoie un body vide
  }
}
