// src/app/core/services/toast.service.ts
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({ providedIn: 'root' })
export class ToastService {
  constructor(private snackBar: MatSnackBar) {}

  showError(message: string): void {
    this.snackBar.open(message, 'Fermer', {
      duration: 2500,
      panelClass: ['error-snackbar'],
    });
  }
  show(message: string): void {
    this.snackBar.open(message, 'Fermer', {
      duration: 1500,
      panelClass: ['success-snackbar'],
    });
  }
}
