import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/authenticate/register/register.component';
import { LoginComponent } from './pages/authenticate/login/login.component';
import { ArticlesComponent } from './pages/Posts/articles/articles.component';
import { authGuard } from './guards/auth.guard';
import { ThemesComponent } from './pages/themes/themes.component';
import { PostComponent } from './pages/Posts/post/post.component';
import { CreateComponent } from './pages/Posts/create/create.component';
import { AccountComponent } from './pages/account/account.component';
import { loggedGuard } from './guards/logged.guard';

export const routes: Routes = [
  { path: '', canActivate: [loggedGuard], component: HomeComponent },
  {
    path: 'register',
    canActivate: [loggedGuard],
    component: RegisterComponent,
  },
  { path: 'login', canActivate: [loggedGuard], component: LoginComponent },
  { path: 'articles', canActivate: [authGuard], component: ArticlesComponent },
  { path: 'themes', canActivate: [authGuard], component: ThemesComponent },
  {
    path: 'post/:id',
    canActivate: [authGuard],
    component: PostComponent,
  },
  {
    path: 'articles/create',
    canActivate: [authGuard],
    component: CreateComponent,
  },
  { path: 'account', canActivate: [authGuard], component: AccountComponent },
];
