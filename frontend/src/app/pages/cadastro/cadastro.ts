import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-cadastro',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './cadastro.html',
  styleUrl: './cadastro.scss'
})
export class CadastroComponent {
  nome = '';
  email = '';
  senha = '';
  endereco = '';
  motorista = false;
  erro = '';
  carregando = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  cadastrar() {
    this.erro = '';
    this.carregando = true;

    this.authService.registro({
      nome: this.nome,
      email: this.email,
      senha: this.senha,
      endereco: this.endereco,
      motorista: this.motorista
    }).subscribe({
      next: () => this.router.navigate(['/caronas']),
      error: (err) => {
        this.erro = err.status === 409
          ? 'Este email já está cadastrado.'
          : 'Erro ao cadastrar. Tente novamente.';
        this.carregando = false;
      }
    });
  }
}
