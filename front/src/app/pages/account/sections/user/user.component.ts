import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { RegisterRequest } from 'src/app/interfaces/registerRequest';

@Component({
  selector: 'app-user-section',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss',
})
export class UserComponent {
  @Input() user: { username: string; email: string } | null = null;
  @Output() save = new EventEmitter<{
    username: string;
    email: string;
    password: string;
  }>();

  form = {
    username: '',
    email: '',
    password: '',
  };

  ngOnChanges() {
    if (this.user) {
      this.form.username = this.user.username;
      this.form.email = this.user.email;
    }
  }

  onSubmit() {
    const payload: RegisterRequest = {
      username: this.form.username,
      email: this.form.email,
      password: this.form.password,
    };
    this.save.emit(payload);
  }
}
