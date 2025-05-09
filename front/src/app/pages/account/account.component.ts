import { Component } from '@angular/core';
import { RegisterRequest } from 'src/app/interfaces/registerRequest';
import { UserService } from 'src/app/services/user.service';
import { UserComponent } from './sections/user/user.component';
import { Topic } from 'src/app/interfaces/Topic';
import { TopicService } from 'src/app/services/topic.service';
import { SubscriptionsComponent } from './sections/subscriptions/subscriptions.component';
import { HeaderComponent } from '../../components/Layout/header/header.component';
import { ToastService } from 'src/app/services/toast.service';
import { Subject, takeUntil } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-account',
  imports: [UserComponent, SubscriptionsComponent, HeaderComponent],
  templateUrl: './account.component.html',
  styleUrl: './account.component.scss',
})
export class AccountComponent {
  subscribedTopics: Topic[] = [];
  constructor(
    private userService: UserService,
    private topicService: TopicService,
    private toastService: ToastService,
    private authService: AuthService
  ) {}
  userData: RegisterRequest | null = null;
  private destroy$ = new Subject<void>();
  ngOnInit(): void {
    this.userService
      .getCurrentUser()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (user) => (this.userData = user),
        error: (err) =>
          console.error('Erreur lors du chargement des infos utilisateur', err),
      });

    this.topicService.getTopics().subscribe({
      next: (topics) => {
        this.subscribedTopics = topics.filter((t) => t.subscribed);
      },
    });
  }

  onUpdateUser(data: RegisterRequest) {
    this.userService
      .updateUser(data)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (updated) => {
          this.userData = updated;
          this.toastService.show('Informations mises à jour');
          if (data.password) {
            this.authService.logout();
            window.location.reload();
          }
        },
      });
  }

  onUnsubscribe(topicId: number) {
    this.topicService
      .unsubscribeFromTopic(topicId)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          this.subscribedTopics = this.subscribedTopics.filter(
            (t) => t.id !== topicId
          );
        },
      });
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
