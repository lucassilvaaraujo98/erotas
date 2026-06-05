import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CaronaService } from '../../services/carona.service';
import { SolicitacaoService } from '../../services/solicitacao.service';

@Component({
  selector: 'app-minhas-caronas',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './minhas-caronas.html',
  styleUrl: './minhas-caronas.scss'
})
export class MinhasCaronasComponent implements OnInit {
  caronas: any[] = [];
  carregando = false;
  mensagem = '';

  constructor(
    private caronaService: CaronaService,
    private solicitacaoService: SolicitacaoService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef,
    public router: Router
  ) {}

  ngOnInit() { this.carregar(); }

  carregar() {
    this.carregando = true;
    this.mensagem = '';

    this.caronaService.minhasCaronas().subscribe({
      next: (dados) => {
        this.caronas = [...dados];
        this.carregando = false;
        if (!dados || dados.length === 0) {
          this.mensagem = 'Você ainda não ofereceu nenhuma carona.';
        }
        this.cdr.detectChanges(); // ← força atualização da tela
      },
      error: (err) => {
        console.error('ERRO:', err);
        this.mensagem = 'Erro ao carregar suas caronas.';
        this.carregando = false;
        this.cdr.detectChanges();
      }
    });
  }

  confirmar(solicitacaoId: number) {
    this.solicitacaoService.confirmar(solicitacaoId).subscribe({
      next: () => { alert('Passageiro aceito!'); this.carregar(); },
      error: (err) => alert(err.error?.erro || 'Erro ao aceitar.')
    });
  }

  recusar(solicitacaoId: number) {
    this.solicitacaoService.recusar(solicitacaoId).subscribe({
      next: () => { alert('Passageiro recusado.'); this.carregar(); },
      error: (err) => alert(err.error?.erro || 'Erro ao recusar.')
    });
  }

  corStatus(status: string): string {
    if (status === 'ACEITA')   return 'aceita';
    if (status === 'RECUSADA') return 'recusada';
    return 'pendente';
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