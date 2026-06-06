# 🚗 e-Rotas — Uma mão na roda pra quem precisa

Sistema de compartilhamento de caronas desenvolvido para a comunidade de Paranaguá–PR, como projeto de Atividade Extensionista III do curso de Bacharelado em Engenharia de Software — UNINTER.

---

## Sobre o projeto

O e-Rotas conecta motoristas e passageiros que percorrem rotas similares, promovendo mobilidade sustentável e reduzindo o número de veículos nas vias da cidade.

**Tecnologias utilizadas:**

| Camada | Tecnologia |
|---|---|
| Backend | Java 17 + Spring Boot 4 |
| Segurança | Spring Security + JWT |
| Banco de dados | MySQL 8 + Flyway |
| Frontend | Angular 17 + TypeScript |
| Infraestrutura | Docker + Docker Compose + Nginx |

---

## Como rodar o projeto

### Pré-requisitos

Instale apenas o **Docker Desktop** na sua máquina:

- [Docker Desktop para Windows/Mac](https://www.docker.com/products/docker-desktop)
- Linux: `sudo apt install docker.io docker-compose`

Não é necessário instalar Java, Maven, Node ou Angular — o Docker cuida de tudo.

---

### 1. Clone o repositório

```bash 
git clone -b docker https://github.com/lucassilvaaraujo98/erotas.git
cd erotas
```

---

### 2. Verifique a estrutura de pastas

Após clonar, a estrutura deve estar assim:

```
erotas/
  ├── backend/          ← projeto Spring Boot
  ├── frontend/         ← projeto Angular
  ├── docker-compose.yml
  └── README.md
```

---

### 3. Suba o projeto com Docker

```bash
docker-compose up --build
```

Na primeira execução o Docker vai:
- Baixar as imagens base (MySQL, Java, Node, Nginx)
- Compilar o backend Spring Boot
- Compilar o frontend Angular
- Criar o banco de dados e aplicar as migrations
- Subir os três serviços

>  A primeira execução pode levar entre 3 e 5 minutos dependendo da sua internet e hardware.

---

### 4. Acesse o sistema

Após ver a mensagem `Started ErotasBackendApplication` no log, acesse:

```
http://localhost
```

---

## Estrutura dos serviços Docker

| Serviço | Porta | Descrição |
|---|---|---|
| Frontend (Nginx) | 80 | Interface Angular |
| Backend (Spring Boot) | 8080 | API REST |
| MySQL | 3306 | Banco de dados |

---

## Primeiro acesso

### Criando um motorista

1. Acesse `http://localhost`
2. Clique em **Cadastre-se**
3. Preencha nome, email, senha e endereço
4. Marque a opção **"Sou motorista e quero oferecer caronas"**
5. Clique em **Criar conta**

O motorista já fica habilitado automaticamente ao se cadastrar com essa opção.

### Criando um passageiro

Repita o processo sem marcar a opção de motorista.

---

## Comandos úteis

**Subir o projeto:**
```bash
docker-compose up --build
```

**Subir em segundo plano:**
```bash
docker-compose up --build -d
```

**Ver logs em tempo real:**
```bash
docker-compose logs -f
```

**Ver logs apenas do backend:**
```bash
docker-compose logs backend -f
```

**Parar todos os serviços:**
```bash
docker-compose down
```

**Parar e apagar os dados do banco:**
```bash
docker-compose down -v
```

**Reiniciar sem rebuild:**
```bash
docker-compose restart
```

---

## Acessar o banco de dados

Com o projeto rodando, acesse o MySQL pelo terminal:

```bash
docker exec -it erotas-mysql-1 mysql -u root -perotas123
```

Comandos úteis dentro do MySQL:

```sql
USE erotas_db;

-- Ver todos os usuários
SELECT id, nome, email, dtype FROM usuario;

-- Ver motoristas e status de habilitação
SELECT u.id, u.nome, u.email, m.habilitado
FROM usuario u
JOIN motorista m ON u.id = m.id;

-- Ver caronas cadastradas
SELECT id, origem, destino, data_hora, vagas_disponiveis
FROM carona;

-- Ver solicitações
SELECT s.id, u.nome AS passageiro, c.origem, c.destino, s.status
FROM solicitacao s
JOIN usuario u ON s.passageiro_id = u.id
JOIN carona c ON s.carona_id = c.id;

-- Habilitar motorista manualmente (se necessário)
UPDATE motorista SET habilitado = 1 WHERE id = 1;
```

---

## Acessando na rede local

Para que outros computadores da mesma rede acessem o sistema:

**1.** Descubra o IP da máquina que está rodando o Docker:

```bash
# Windows
ipconfig

# Linux/Mac
ip addr show
```

**2.** Libere as portas 80 e 8080 no firewall:

```bash
# Windows (PowerShell como administrador)
netsh advfirewall firewall add rule name="eRotas HTTP" protocol=TCP dir=in localport=80 action=allow
netsh advfirewall firewall add rule name="eRotas API" protocol=TCP dir=in localport=8080 action=allow
```

**3.** Os outros computadores acessam via:

```
http://IP_DA_MAQUINA
```

Exemplo: `http://192.168.1.50`

---

##  Variáveis de ambiente

As configurações estão no `docker-compose.yml`. Para personalizar:

| Variável | Padrão | Descrição |
|---|---|---|
| `MYSQL_ROOT_PASSWORD` | `erotas123` | Senha do MySQL |
| `MYSQL_DATABASE` | `erotas_db` | Nome do banco |
| `JWT_SECRET` | `erotas_secret_key_...` | Chave de assinatura do JWT |
| `JWT_EXPIRATION` | `86400000` | Expiração do token (24h em ms) |

>  Em produção, troque o `JWT_SECRET` por uma string longa e aleatória.

---

##  Endpoints da API

Todos os endpoints protegidos exigem o header:
```
Authorization: Bearer SEU_TOKEN_JWT
```

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| POST | `/api/auth/registro` | Não | Cadastro de usuário |
| POST | `/api/auth/login` | Não | Login e geração do JWT |
| GET | `/api/caronas` | Sim | Buscar caronas disponíveis |
| POST | `/api/caronas` | Sim | Cadastrar nova carona |
| GET | `/api/caronas/historico` | Sim | Histórico do motorista |
| GET | `/api/caronas/minhas` | Sim | Caronas do motorista com solicitações |
| POST | `/api/solicitacoes` | Sim | Solicitar carona |
| GET | `/api/solicitacoes` | Sim | Listar todas as solicitações |
| GET | `/api/solicitacoes/historico` | Sim | Histórico do passageiro |
| PUT | `/api/solicitacoes/{id}/confirmar` | Sim | Motorista aceita solicitação |
| PUT | `/api/solicitacoes/{id}/recusar` | Sim | Motorista recusa solicitação |
| PUT | `/api/usuarios/{id}/habilitar` | Sim | Habilitar motorista |

---

## Testando a API com Postman

1. Importe os endpoints na collection do Postman
2. Faça `POST /api/auth/login` e copie o token da resposta
3. Nas demais requisições, adicione em **Authorization → Bearer Token**

Exemplo de body para login:
```json
{
  "email": "seu@email.com",
  "senha": "suasenha"
}
```

Exemplo de body para cadastrar carona:
```json
{
  "origem": "Centro de Paranaguá",
  "destino": "Porto de Paranaguá",
  "dataHora": "2026-06-10T08:00:00",
  "vagasDisponiveis": 3
}
```

---

## Solução de problemas comuns

**Erro: `port is already allocated`**

A porta 80 ou 3306 já está em uso. Pare outros serviços ou mude as portas no `docker-compose.yml`.

**Erro: `dependency failed to start: container is unhealthy`**

O backend demorou mais que o esperado para subir. Rode novamente:
```bash
docker-compose up
```

**Erro: `Flyway checksum mismatch`**

O script de migration foi alterado após já ter sido aplicado. Recrie o banco:
```bash
docker-compose down -v
docker-compose up --build
```

**Telas ficam em "Carregando..."**

Faça logout e login novamente para renovar o token JWT. Se persistir, verifique se o backend está rodando:
```bash
docker-compose logs backend --tail=20
```

**Erro 403 ao acessar de outro computador**

Verifique se as portas estão liberadas no firewall da máquina host conforme a seção "Acessando na rede local".

---

## Estrutura do projeto

```
erotas/
  ├── backend/
  │     ├── src/main/java/com/erotas/erotas_backend/
  │     │     ├── config/          (JWT, Security, CORS)
  │     │     ├── controller/      (endpoints REST)
  │     │     ├── dto/             (objetos de transferência)
  │     │     ├── exception/       (tratamento de erros)
  │     │     ├── model/           (entidades JPA)
  │     │     ├── repository/      (acesso ao banco)
  │     │     └── service/         (regras de negócio)
  │     ├── src/main/resources/
  │     │     ├── db/migration/    (scripts Flyway)
  │     │     └── application.yml
  │     ├── Dockerfile
  │     └── pom.xml
  │
  ├── frontend/
  │     ├── src/app/
  │     │     ├── pages/           (login, cadastro, caronas, etc.)
  │     │     ├── services/        (auth, carona, solicitacao)
  │     │     ├── guards/          (autenticação de rotas)
  │     │     ├── interceptors/    (JWT automático)
  │     │     └── environments/    (URLs da API)
  │     ├── nginx.conf
  │     └── Dockerfile
  │
  └── docker-compose.yml
```

---

## Autor

**Lucas Silva de Araújo** — RU 3604185  
Bacharelado em Engenharia de Software — UNINTER  
Atividade Extensionista III — 2026

---

## Licença

Este projeto foi desenvolvido para fins acadêmicos.  
Paranaguá–PR 
EOF
