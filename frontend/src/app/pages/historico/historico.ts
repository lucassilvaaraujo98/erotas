import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { SolicitacaoService, Solicitacao } from '../../services/solicitacao.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-historico',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './historico.html',
  styleUrl: './historico.scss'
})
export class HistoricoComponent implements OnInit {
  solicitacoes: Solicitacao[] = [];
  carregando = false;
  mensagem = '';

  constructor(
    private solicitacaoService: SolicitacaoService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  ngOnInit() {
    this.carregarHistorico();
  }


carregarHistorico() {
  this.carregando = true;
  this.mensagem = '';
  this.solicitacaoService.historico().subscribe({
    next: (dados) => {
      this.solicitacoes = dados;
      this.carregando = false;
      if (dados.length === 0) {
        this.mensagem = 'Você ainda não fez nenhuma solicitação. Que tal pegar uma carona agora?';
      }
      this.cdr.detectChanges();
    },
    error: (err) => {
      console.error('Erro historico:', err.status, err.message);
      this.mensagem = err.status === 401
        ? 'Sessão expirada. Faça login novamente.'
        : 'Erro ao carregar histórico.';
      this.carregando = false;
      this.cdr.detectChanges();
    }
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

  formatarData(data: string): string {
    return new Date(data).toLocaleString('pt-BR', {
      day: '2-digit', month: '2-digit', year: 'numeric',
      hour: '2-digit', minute: '2-digit'
    });
  }

  voltar() { this.router.navigate(['/caronas']); }
  sair()   { this.authService.logout(); }
}
