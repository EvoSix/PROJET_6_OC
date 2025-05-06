import { Component } from '@angular/core';

import { CommonModule } from '@angular/common';

import { PostService } from 'src/app/services/post.service';
import { Post } from 'src/app/interfaces/Posts';
import { RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { HeaderComponent } from '../../../components/Layout/header/header.component';

@Component({
  selector: 'app-articles',
  imports: [CommonModule, RouterModule, MatIconModule, HeaderComponent],
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.scss',
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
      },
    });
  }

  changeOrder() {
    this.order = this.order === 'asc' ? 'desc' : 'asc';
    this.loadPosts();
  }
}
