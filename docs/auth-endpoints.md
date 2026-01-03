# 🔐 Authentication Endpoints — Churn Insight (API Java)

Esta documentação descreve os endpoints de autenticação implementados no branch `feature/auth`.

## ✅ Visão Geral

A API fornece:
- **Cadastro de usuário** (Register)
- **Login e geração de token** (JWT)

Os endpoints de autenticação são públicos. Os demais endpoints serão gradualmente protegidos nas próximas etapas.

---

## 1) 📌 Register — Criar conta

### Endpoint
POST `/auth/register`

### Body (JSON)
```json
{
  "email": "matheus@test.com",
  "password": "123456",
  "name": "Matheus"
}
```

### Regras

* `email`: obrigatório e único
* `password`: obrigatório
* `name`: obrigatório
* A senha é armazenada **criptografada** (`PasswordEncoder`)

### Response (201)

```json
{
  "id": 1,
  "email": "matheus@test.com",
  "name": "Matheus"
}
```

### Possíveis erros

#### 409 — Email já cadastrado

```json
{
  "type": "EMAIL_ALREADY_REGISTERED",
  "status": 409,
  "message": "Email already registered: matheus@test.com",
  "path": "/auth/register",
  "requestId": "..."
}
```

#### 400 — Validação

```json
{
  "type": "VALIDATION_ERROR",
  "status": 400,
  "message": "Invalid request body",
  "path": "/auth/register",
  "requestId": "...",
  "errors": [
    { "field": "email", "message": "must not be blank" }
  ]
}
```

---

## 2) 🔑 Login — Autenticar e obter token

### Endpoint

POST `/auth/login`

### Body (JSON)

```json
{
  "email": "matheus@test.com",
  "password": "123456"
}
```

### Response (200)

```json
{
  "type": "Bearer",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Como usar o token

Em requisições protegidas, enviar no header:

```
Authorization: Bearer <token>
```

Exemplo:

```
Authorization: Bearer eyJhbGciOi...
```

### Possíveis erros

#### 401 — Credenciais inválidas

```json
{
  "type": "INVALID_CREDENTIALS",
  "status": 401,
  "message": "Invalid email or password",
  "path": "/auth/login",
  "requestId": "..."
}
```

---

## 🧩 Observações técnicas

### Componentes principais

* `AuthController`: endpoints de autenticação
* `AuthService`: regras de negócio (register/login)
* `TokenService`: geração e validação de token JWT
* `AccountDetailsService`: integração com Spring Security
* `Account`: entidade de usuário
* `AccountRepository`: persistência

### Status

* Funcionalidade testada localmente
* Branch `feature/auth` pronto para merge

---

