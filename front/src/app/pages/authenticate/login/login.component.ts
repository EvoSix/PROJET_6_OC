import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { CommonModule } from '@angular/common';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { HeaderComponent } from '../../../components/Layout/header/header.component';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-login',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatSnackBarModule,
    MatIconModule,
    HeaderComponent,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  loginForm: FormGroup;
  submitted = false;
  loading = false;
  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private snackbar: MatSnackBar,
    private router: Router
  ) {
    this.loginForm = this.formBuilder.group({
      identifier: ['', Validators.required], // email ou username
      password: ['', Validators.required],
    });
  }

  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    if (this.loginForm.invalid) {
      return;
    }
    this.loading = true;
    const payload = {
      email: this.f['identifier'].value,
      password: this.f['password'].value,
    };
    this.authService.login(payload).subscribe({
      next: (response) => {
        this.loading = false;
        localStorage.setItem('token', response.token ?? '');
        this.authService.setLoggedIn(response.token ?? '');
        this.snackbar.open(response.message, 'Fermer', { duration: 3000 });
        this.router.navigateByUrl('/articles');
      },
      error: (err) => {
        this.loading = false;
        const msg =
          err?.error?.message || err?.error?.error || 'Erreur inconnue';
        this.snackbar.open(msg, 'Fermer', { duration: 3000 });
      },
    });
  }
}
