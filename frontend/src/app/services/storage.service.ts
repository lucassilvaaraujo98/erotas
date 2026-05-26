import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class StorageService {

  private readonly TOKEN_KEY = 'erotas_token';
  private readonly USER_KEY  = 'erotas_user';

  salvarToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  salvarUsuario(usuario: { id: number; nome: string }): void {
    localStorage.setItem(this.USER_KEY, JSON.stringify(usuario));
  }

  getUsuario(): { id: number; nome: string } | null {
    const u = localStorage.getItem(this.USER_KEY);
    return u ? JSON.parse(u) : null;
  }

  isLogado(): boolean {
    return !!this.getToken();
  }

  limpar(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
  }
}
