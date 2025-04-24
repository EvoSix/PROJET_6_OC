import { Component } from '@angular/core';
import { HeaderComponent } from "../../../components/Layout/header/header.component";
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/module.d-vndDeG-q';
import { MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-articles',
  imports: [
    HeaderComponent],
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.scss'
})
export class ArticlesComponent {

}
