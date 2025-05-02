import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Topic } from 'src/app/interfaces/Topic';

@Component({
  selector: 'app-subscriptions',
  imports: [CommonModule],
  templateUrl: './subscriptions.component.html',
  styleUrl: './subscriptions.component.scss'
})
export class SubscriptionsComponent {
  @Input() topics: Topic[] = [];
  @Output() unsubscribe = new EventEmitter<number>();
  onUnsubscribe(topic: Topic) {
    this.unsubscribe.emit(topic.id);
  }
}
