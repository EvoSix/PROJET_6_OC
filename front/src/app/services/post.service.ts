import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Post } from '../interfaces/Posts';
import { CommentArticle } from '../interfaces/Comment';
import { PostRequest } from '../interfaces/postRequest';
interface PostWithCommentsResponseDTO {
  post: Post;
  comments: CommentArticle[];
}
@Injectable({
  providedIn: 'root',
})
export class PostService {
  private http = inject(HttpClient);
  private apiUrl = environment.baseUrl;
  getPostWithComments(id: string) {
    return this.http.get<PostWithCommentsResponseDTO>(
      `${this.apiUrl}posts/${id}`
    );
  }
  getAllPosts(order: 'asc' | 'desc' = 'desc'): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.apiUrl}posts?order=${order}`);
  }

  commentOnPost(postId: string, request: { content: string }) {
    return this.http.post(`${this.apiUrl}posts/${postId}/comments`, request);
  }
  createPost(postData: PostRequest) {
    return this.http.post(`${this.apiUrl}posts`, postData);
  }
}
