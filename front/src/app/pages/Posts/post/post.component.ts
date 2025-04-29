import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Post } from 'src/app/interfaces/Posts';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-post',
  imports: [CommonModule],
  templateUrl: './post.component.html',
  styleUrl: './post.component.scss',
})
export class PostComponent {
  articleId: string | null = null;
  post: Post | null = null;
  comments: Comment[] = [];
  loading = true;
  constructor(
    private route: ActivatedRoute,
    private postService: PostService
  ) {}
  ngOnInit(): void {
    this.articleId = this.route.snapshot.paramMap.get('id');
    if (this.articleId) {
      this.fetchPostWithComments(this.articleId);
      console.log(this.post);
      console.log(this.comments);
    }
  }

  fetchPostWithComments(id: string) {
    this.loading = true;
    this.postService.getPostWithComments(id).subscribe({
      next: (data) => {
        this.post = data.post;
        this.comments = data.comments;
        this.loading = false;
      },
      error: (err) => {
        console.error("Erreur lors du chargement de l'article", err);
        this.loading = false;
      },
    });
  }
}
