import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

export interface Solicitacao {
  id: number;
  passageiro: { id: number; nome: string; email: string };
  carona: any;
  status: 'PENDENTE' | 'ACEITA' | 'RECUSADA';
}

@Injectable({ providedIn: 'root' })
export class SolicitacaoService {

  private readonly url = `${environment.apiUrl}/solicitacoes`;

  constructor(private http: HttpClient) {}

  solicitar(caronaId: number) {
    return this.http.post<Solicitacao>(this.url, { caronaId });
  }

  listarTodas() {
    return this.http.get<Solicitacao[]>(this.url);
  }

  historico() {
    return this.http.get<Solicitacao[]>(`${this.url}/historico`);
  }

  confirmar(id: number) {
    return this.http.put<Solicitacao>(`${this.url}/${id}/confirmar`, {});
  }

  recusar(id: number) {
    return this.http.put<Solicitacao>(`${this.url}/${id}/recusar`, {});
  }
}
