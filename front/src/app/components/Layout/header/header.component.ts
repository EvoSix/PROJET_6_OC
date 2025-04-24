import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
@Component({
  selector: 'app-header',
  imports: [   CommonModule,
    RouterModule,
    MatToolbarModule,
    MatButtonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  private authService = inject(AuthService);
  private router = inject(Router);
  isLoggedIn$: Observable<boolean> = this.authService.isLoggedIn$;
  
  logout() {
    this.authService.logout();
   this.router.navigateByUrl('/');

  }
}
