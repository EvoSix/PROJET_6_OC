import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { Router } from '@angular/router';
import { PostRequest } from 'src/app/interfaces/postRequest';
import { PostService } from 'src/app/services/post.service';
import { HeaderComponent } from "../../../components/Layout/header/header.component";
import { TopicService } from 'src/app/services/topic.service';
import { Topic } from 'src/app/interfaces/Topic';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-create',
  imports: [CommonModule,ReactiveFormsModule, MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule, HeaderComponent],
  templateUrl: './create.component.html',
  styleUrl: './create.component.scss'
})
export class CreateComponent {
 
  postForm: FormGroup;
  topics: Topic[] = [];
  constructor(private fb: FormBuilder, private postService: PostService,   private topicService: TopicService,private router: Router) {
    this.postForm=this.fb.group({
      title: ['', Validators.required],
      content: ['', Validators.required],
      topicId: ['', Validators.required],
    });
  }
  ngOnInit(): void {
    this.topicService.getAllTopics().subscribe({
      next: (topics) => (this.topics = topics),
      error: (err) => console.error('Erreur lors du chargement des topics', err),
    });
  }

  onSubmit() {
    if (this.postForm.invalid) {return};

    const payload: PostRequest = {
      title: this.postForm.value.title,
      content: this.postForm.value.content,
      topicId: this.postForm.value.topicId
    };
    this.postService.createPost(payload).subscribe({
      next: () => this.router.navigate(['/posts']),
      error: (err) => console.error('Erreur lors de la création', err),
    });
  }

}
