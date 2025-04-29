import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/authenticate/register/register.component';
import { LoginComponent } from './pages/authenticate/login/login.component';
import { ArticlesComponent } from './pages/Posts/articles/articles.component';
import { authGuard } from './guards/auth.guard';
import { ThemesComponent } from './pages/themes/themes.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'articles', canActivate: [authGuard], component: ArticlesComponent },
  { path: 'themes', canActivate: [authGuard], component: ThemesComponent },
];
