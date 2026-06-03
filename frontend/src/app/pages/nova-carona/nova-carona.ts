import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CaronaService, CaronaRequest } from '../../services/carona.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-nova-carona',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './nova-carona.html',
  styleUrl: './nova-carona.scss'
})
export class NovaCaronaComponent {
  origem = '';
  destino = '';
  dataHora = '';
  vagasDisponiveis = 1;
  erro = '';
  sucesso = '';
  carregando = false;

  constructor(
    private caronaService: CaronaService,
    private authService: AuthService,
    private router: Router
  ) {}

cadastrar() {
  this.erro = '';
  this.sucesso = '';

  if (!this.origem || !this.destino || !this.dataHora) {
    this.erro = 'Preencha todos os campos obrigatórios.';
    return;
  }

  this.carregando = true;

  // Adiciona os segundos se não tiver
  const dataFormatada = this.dataHora.length === 16
    ? this.dataHora + ':00'
    : this.dataHora;

  const carona: CaronaRequest = {
    origem: this.origem,
    destino: this.destino,
    dataHora: dataFormatada,
    vagasDisponiveis: this.vagasDisponiveis
  };

  this.caronaService.cadastrar(carona).subscribe({
    next: () => {
      this.sucesso = 'Carona cadastrada com sucesso!';
      this.carregando = false;
      setTimeout(() => this.router.navigate(['/caronas']), 2000);
    },
    error: (err) => {
      this.erro = err.error?.erro || 'Erro ao cadastrar. Verifique se você é um motorista habilitado.';
      this.carregando = false;
    }
  });
}

  voltar() { this.router.navigate(['/caronas']); }
  sair()   { this.authService.logout(); }
}