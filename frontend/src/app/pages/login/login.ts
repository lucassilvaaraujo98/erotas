import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class LoginComponent {
  email = '';
  senha = '';
  erro = '';
  carregando = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  entrar() {
    this.erro = '';
    this.carregando = true;

    this.authService.login({ email: this.email, senha: this.senha })
      .subscribe({
        next: () => this.router.navigate(['/caronas']),
        error: () => {
          this.erro = 'Email ou senha incorretos.';
          this.carregando = false;
        }
      });
  }
}
