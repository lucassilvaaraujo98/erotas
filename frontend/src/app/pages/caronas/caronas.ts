import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CaronaService, Carona } from '../../services/carona.service';
import { SolicitacaoService } from '../../services/solicitacao.service';
import { AuthService } from '../../services/auth.service';
import { StorageService } from '../../services/storage.service';

@Component({
  selector: 'app-caronas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './caronas.html',
  styleUrl: './caronas.scss'
})
export class CaronasComponent implements OnInit {
  caronas: Carona[] = [];
  origem = '';
  destino = '';
  carregando = false;
  mensagem = '';
  nomeUsuario = '';

  constructor(
    private caronaService: CaronaService,
    private solicitacaoService: SolicitacaoService,
    private authService: AuthService,
    private storage: StorageService,
    public router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    const usuario = this.storage.getUsuario();
    if (usuario) this.nomeUsuario = usuario.nome;
    this.buscar();
  }

  buscar() {
    this.carregando = true;
    this.mensagem = '';

    this.caronaService.buscar(this.origem, this.destino).subscribe({
      next: (dados) => {
        this.caronas = dados;
        if (dados.length === 0) {
          this.mensagem = 'Nenhuma carona disponível para essa rota.';
        }
        this.carregando = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.mensagem = 'Erro ao buscar caronas.';
        this.carregando = false;
        this.cdr.detectChanges();
      }
    });
  }

  solicitar(caronaId: number) {
    this.solicitacaoService.solicitar(caronaId).subscribe({
      next: () => alert('Solicitação enviada com sucesso!'),
      error: (err) => alert(err.error?.erro || 'Erro ao solicitar carona.')
    });
  }

  irParaHistorico() {
    this.router.navigate(['/historico']);
  }

  irParaSolicitacoes() {
    this.router.navigate(['/solicitacoes']);
  }

  sair() {
    this.authService.logout();
  }

  formatarData(data: string): string {
    return new Date(data).toLocaleString('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }
}
