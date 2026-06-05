import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { StorageService } from './storage.service';

export interface Carona {
  id: number;
  origem: string;
  destino: string;
  dataHora: string;
  vagasDisponiveis: number;
  nomeMotorista: string;
  disponivel: boolean;
}

export interface CaronaRequest {
  origem: string;
  destino: string;
  dataHora: string;
  vagasDisponiveis: number;
}

@Injectable({ providedIn: 'root' })
export class CaronaService {

  private readonly url = `${environment.apiUrl}/caronas`;

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

  buscar(origem?: string, destino?: string) {
    let params = new HttpParams();
    if (origem)  params = params.set('origem', origem);
    if (destino) params = params.set('destino', destino);
    return this.http.get<Carona[]>(this.url, { ...this.headers(), params });
  }

  cadastrar(carona: CaronaRequest) {
    return this.http.post<Carona>(this.url, carona, this.headers());
  }

  historico() {
    return this.http.get<Carona[]>(`${this.url}/historico`, this.headers());
  }

  minhasCaronas() {
    return this.http.get<any[]>(`${this.url}/minhas`, this.headers());
  }
}