import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-post-comment-form',
  imports: [CommonModule, FormsModule],
  templateUrl: './post-comment-form.component.html',
  styleUrl: './post-comment-form.component.scss',
})
export class PostCommentFormComponent {
  commentContent: string = '';

  @Output() submitComment = new EventEmitter<string>();

  onSubmit() {
    if (this.commentContent.trim()) {
      this.submitComment.emit(this.commentContent.trim());
      this.commentContent = '';
    }
  }
}
