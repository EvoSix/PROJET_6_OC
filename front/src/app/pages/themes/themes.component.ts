import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Topic } from 'src/app/interfaces/Topic';
import { HeaderComponent } from '../../components/Layout/header/header.component';
import { CommonModule } from '@angular/common';
import { TopicService } from 'src/app/services/topic.service';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-themes',
  imports: [HeaderComponent, CommonModule],
  templateUrl: './themes.component.html',
  styleUrl: './themes.component.scss',
})
export class ThemesComponent {
  topics: Topic[] = [];

  constructor(private topicService: TopicService) {}

  ngOnInit(): void {
    this.fetchTopics();
  }
  private destroy$ = new Subject<void>();
  fetchTopics(): void {
    this.topicService.getTopics().subscribe((data) => {
      this.topics = data;
    });
  }

  subscribeTopic(topic: Topic): void {
    this.topicService.subscribeToTopic(topic.id).subscribe({
      next: () => {
        topic.subscribed = true;
      },
      error: (err) => {
        console.error('Erreur lors de l’abonnement :', err);
      },
    });
  }
  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
