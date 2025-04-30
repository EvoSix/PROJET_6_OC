import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Post } from 'src/app/interfaces/Posts';
import { PostService } from 'src/app/services/post.service';
import { CommentArticle } from 'src/app/interfaces/Comment';
import { PostCommentsComponent } from '../components/post-comments/post-comments.component';
import { PostContentComponent } from '../components/post-content/post-content.component';
import { PostCommentFormComponent } from "../components/post-comment-form/post-comment-form.component";
import { HeaderComponent } from "../../../components/Layout/header/header.component";
@Component({
  selector: 'app-post',
  imports: [
    CommonModule,
    RouterLink,
    PostCommentsComponent,
    PostContentComponent,
    PostCommentFormComponent,
    HeaderComponent
],
  templateUrl: './post.component.html',
  styleUrl: './post.component.scss',
})
export class PostComponent {
  articleId: string | null = null;
  post: Post | null = null;
  comments: CommentArticle[] = [];
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
  submitNewComment(content: string) {
    if (!this.articleId) return;

    this.postService.commentOnPost(this.articleId, { content }).subscribe({
      next: () => {
        // Optionnel : tu peux recharger les commentaires ici
        this.fetchPostWithComments(this.articleId!);
      },
      error: (err) => {
        console.error("Erreur lors de l'envoi du commentaire :", err);
      },
    });
  }
}
