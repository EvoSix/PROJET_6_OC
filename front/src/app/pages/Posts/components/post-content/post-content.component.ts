import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { Post } from 'src/app/interfaces/Posts';

@Component({
  selector: 'app-post-content',
  imports: [CommonModule, RouterLink],
  templateUrl: './post-content.component.html',
  styleUrl: './post-content.component.scss',
})
export class PostContentComponent {
  @Input() post!: Post;
}
