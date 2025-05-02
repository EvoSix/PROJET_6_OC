import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { RegisterRequest } from 'src/app/interfaces/registerRequest';
import { AuthService } from 'src/app/services/auth.service';
import { HeaderComponent } from '../../../components/Layout/header/header.component';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
@Component({
  selector: 'app-register',
  imports: [
    ReactiveFormsModule,
    HeaderComponent,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent {
  registerForm: FormGroup;
  isSubmitted = false;

  constructor(
    private formRegister: FormBuilder,
    private authService: AuthService
  ) {
    this.registerForm = this.formRegister.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
    });
  }
  onSubmit() {
    this.isSubmitted = true;
    if (this.registerForm.invalid) {
   

      return;
    }

    const payload: RegisterRequest = {
      username: this.registerForm.value.username,
      email: this.registerForm.value.email,
      password: this.registerForm.value.password,
    };
    this.authService.register(payload).subscribe({
      next: (response) => {
        console.log(response);
      },
      error: (error) => {
        console.error(error);
      },
    });
  }
}
