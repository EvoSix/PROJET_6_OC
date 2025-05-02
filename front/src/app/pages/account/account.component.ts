import { Component } from '@angular/core';
import { RegisterRequest } from 'src/app/interfaces/registerRequest';
import { UserService } from 'src/app/services/user.service';
import { UserComponent } from './sections/user/user.component';
import { Topic } from 'src/app/interfaces/Topic';
import { TopicService } from 'src/app/services/topic.service';
import { SubscriptionsComponent } from './sections/subscriptions/subscriptions.component';
import { HeaderComponent } from "../../components/Layout/header/header.component";

@Component({
  selector: 'app-account',
  imports: [UserComponent, SubscriptionsComponent, HeaderComponent],
  templateUrl: './account.component.html',
  styleUrl: './account.component.scss'
})
export class AccountComponent {
  subscribedTopics: Topic[] = [];
  constructor(private userService: UserService,private topicService: TopicService) {}
  userData: RegisterRequest | null = null;
  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe({
      next: (user) => this.userData = user,
      error: (err) => console.error('Erreur lors du chargement des infos utilisateur', err)
    });

    this.topicService.getTopics().subscribe({
      next: (topics) => {
        this.subscribedTopics = topics.filter(t => t.subscribed);
      }
    });
  }

  onUpdateUser(data: RegisterRequest) {
    this.userService.updateUser(data).subscribe({

      next: (updated) => {
        this.userData = updated;
        alert('Mise à jour réussie !');
      },
      error: (err) => alert('Erreur lors de la mise à jour.')
    });
  }


  onUnsubscribe(topicId: number) {
    this.topicService.unsubscribeFromTopic(topicId).subscribe({
      next: () => {
        this.subscribedTopics = this.subscribedTopics.filter(t => t.id !== topicId);
      },
      error: (err) => {
        console.error('Erreur lors du désabonnement', err);
        alert('Échec du désabonnement');
      }
    });
  }
}
