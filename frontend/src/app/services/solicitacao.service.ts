import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { StorageService } from './storage.service';

export interface Solicitacao {
  id: number;
  passageiro: { id: number; nome: string; email: string };
  carona: any;
  status: 'PENDENTE' | 'ACEITA' | 'RECUSADA';
}

@Injectable({ providedIn: 'root' })
export class SolicitacaoService {

  private readonly url = `${environment.apiUrl}/solicitacoes`;

  constructor(
    private http: HttpClient,
    private storage: StorageService
  ) {}

private headers(): { headers: HttpHeaders } {
  // Busca direto do localStorage com a chave exata
  const token = localStorage.getItem('erotas_token');
  console.log('[Service] token encontrado:', token ? 'SIM' : 'NÃO');
  return {
    headers: new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    })
  };
}

  solicitar(caronaId: number) {
    return this.http.post<Solicitacao>(this.url, { caronaId }, this.headers());
  }

  listarTodas() {
    return this.http.get<Solicitacao[]>(this.url, this.headers());
  }

  historico() {
    return this.http.get<Solicitacao[]>(`${this.url}/historico`, this.headers());
  }

  confirmar(id: number) {
    return this.http.put<Solicitacao>(`${this.url}/${id}/confirmar`, {}, this.headers());
  }

  recusar(id: number) {
    return this.http.put<Solicitacao>(`${this.url}/${id}/recusar`, {}, this.headers());
  }
}