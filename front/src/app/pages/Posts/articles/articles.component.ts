import { Component } from '@angular/core';
import { HeaderComponent } from "../../../components/Layout/header/header.component";
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/module.d-vndDeG-q';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { PostService } from 'src/app/services/post.service';
import { Post } from 'src/app/interfaces/Posts';

@Component({
  selector: 'app-articles',
  imports: [CommonModule,
    HeaderComponent],
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.scss'
})
export class ArticlesComponent {
  order: 'asc' | 'desc' = 'desc';
  posts: Post[] = [];
  loading = true;
  constructor(private postService: PostService) {}

  ngOnInit(): void {
    this.loadPosts();
  }

  loadPosts() {
    this.loading = true;
    this.postService.getAllPosts(this.order).subscribe({
      next: (data) => {
        this.posts = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des articles', err);
        this.loading = false;
      }
    });
  }

  changeOrder() {
    this.order = this.order === 'asc' ? 'desc' : 'asc';
    this.loadPosts();
  }

}
