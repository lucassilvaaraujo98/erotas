import { Routes } from '@angular/router';
import { authGuard } from './guards/auths-guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login')
      .then(m => m.LoginComponent)
  },
  {
    path: 'cadastro',
    loadComponent: () => import('./pages/cadastro/cadastro')
      .then(m => m.CadastroComponent)
  },
  {
    path: 'caronas',
    loadComponent: () => import('./pages/caronas/caronas')
      .then(m => m.CaronasComponent),
    canActivate: [authGuard]
  },
  {
    path: 'solicitacoes',
    loadComponent: () => import('./pages/solicitacoes/solicitacoes')
      .then(m => m.SolicitacoesComponent),
    canActivate: [authGuard]
  },
  {
    path: 'historico',
    loadComponent: () => import('./pages/historico/historico')
      .then(m => m.HistoricoComponent),
    canActivate: [authGuard]
  },
  {
    path: 'nova-carona',
    loadComponent: () => import('./pages/nova-carona/nova-carona')
      .then(m => m.NovaCaronaComponent),
    canActivate: [authGuard]
  },

  { path: '**', redirectTo: 'login' }



];
