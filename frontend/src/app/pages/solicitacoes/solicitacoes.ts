import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { SolicitacaoService, Solicitacao } from '../../services/solicitacao.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-solicitacoes',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './solicitacoes.html',
  styleUrl: './solicitacoes.scss'
})
export class SolicitacoesComponent implements OnInit {
  solicitacoes: Solicitacao[] = [];
  carregando = false;
  mensagem = '';

  constructor(
    private solicitacaoService: SolicitacaoService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

ngOnInit() { this.carregar(); }

carregar() {
  this.carregando = true;
  this.mensagem = '';
  this.solicitacaoService.listarTodas().subscribe({
    next: (dados) => {
      this.solicitacoes = dados;
      this.carregando = false;
      if (dados.length === 0) {
        this.mensagem = 'Nenhuma solicitação no momento. Volte mais tarde!';
      }
      this.cdr.detectChanges();
    },
    error: (err) => {
      console.error('Erro solicitacoes:', err.status, err.message);
      this.mensagem = err.status === 401
        ? 'Sessão expirada. Faça login novamente.'
        : 'Erro ao carregar solicitações.';
      this.carregando = false;
      this.cdr.detectChanges();
    }
  });
}

  confirmar(id: number) {
    this.solicitacaoService.confirmar(id).subscribe({
      next: () => {
        alert('Solicitação aceita!');
        this.carregar();
      },
      error: (err) => alert(err.error?.erro || 'Erro ao confirmar.')
    });
  }

  recusar(id: number) {
    this.solicitacaoService.recusar(id).subscribe({
      next: () => {
        alert('Solicitação recusada.');
        this.carregar();
      },
      error: (err) => alert(err.error?.erro || 'Erro ao recusar.')
    });
  }

  corStatus(status: string): string {
    switch (status) {
      case 'ACEITA':   return 'aceita';
      case 'RECUSADA': return 'recusada';
      default:         return 'pendente';
    }
  }

  labelStatus(status: string): string {
    switch (status) {
      case 'ACEITA':   return '✅ Aceita';
      case 'RECUSADA': return '❌ Recusada';
      default:         return '⏳ Pendente';
    }
  }

  voltar() { this.router.navigate(['/caronas']); }
  sair()   { this.authService.logout(); }
}
