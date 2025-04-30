import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { CommentArticle } from 'src/app/interfaces/Comment';

@Component({
  selector: 'app-post-comments',
  imports: [CommonModule],
  templateUrl: './post-comments.component.html',
  styleUrl: './post-comments.component.scss',
})
export class PostCommentsComponent {
  @Input() comments: CommentArticle[] = [];
}
