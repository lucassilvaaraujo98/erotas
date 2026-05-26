import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';

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

  constructor(private http: HttpClient) {}

  buscar(origem?: string, destino?: string) {
    let params = new HttpParams();
    if (origem)  params = params.set('origem', origem);
    if (destino) params = params.set('destino', destino);
    return this.http.get<Carona[]>(this.url, { params });
  }

  cadastrar(carona: CaronaRequest) {
    return this.http.post<Carona>(this.url, carona);
  }

  historico() {
    return this.http.get<Carona[]>(`${this.url}/historico`);
  }
}
