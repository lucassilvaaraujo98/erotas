import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { StorageService } from './storage.service';

export interface AuthResponse {
  token: string;
  nome: string;
  id: number;
}

export interface LoginRequest {
  email: string;
  senha: string;
}

export interface RegisterRequest {
  nome: string;
  email: string;
  senha: string;
  endereco?: string;
  motorista: boolean;
}

@Injectable({ providedIn: 'root' })
export class AuthService {

  private readonly url = `${environment.apiUrl}/auth`;

  constructor(
    private http: HttpClient,
    private storage: StorageService,
    private router: Router
  ) {}

  login(req: LoginRequest) {
    return this.http.post<AuthResponse>(`${this.url}/login`, req).pipe(
      tap(res => {
        this.storage.salvarToken(res.token);
        this.storage.salvarUsuario({ id: res.id, nome: res.nome });
      })
    );
  }

  registro(req: RegisterRequest) {
    return this.http.post<AuthResponse>(`${this.url}/registro`, req).pipe(
      tap(res => {
        this.storage.salvarToken(res.token);
        this.storage.salvarUsuario({ id: res.id, nome: res.nome });
      })
    );
  }

  logout(): void {
    this.storage.limpar();
    this.router.navigate(['/login']);
  }

  isLogado(): boolean {
    return this.storage.isLogado();
  }
}
