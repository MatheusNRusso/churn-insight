# UI Dashboard (Auth + Predict + Stats)

Dashboard web simples (HTML/CSS/JS) para facilitar testes manuais e demonstração da API do **Churn Insight**.

Ele permite:
- Registrar usuário (Register)
- Fazer login (Login) e utilizar **JWT Bearer Token**
- Executar predição (Predict)
- Consultar métricas (Stats)

> Objetivo: facilitar validação, debugging e apresentação (demo) do projeto sem depender de Postman/Swagger.

---

## Onde fica no projeto

Arquivos do dashboard:
- `api-java/src/main/resources/static/ui/`
  - `index.html`
  - `styles.css`
  - `app.js`

Como está em `static/`, o Spring Boot serve esses arquivos automaticamente.

---

## Como executar e acessar

1) Suba a API Java:

- `cd api-java`
- `./mvnw spring-boot:run`

2) Abra o dashboard no navegador:

- `http://localhost:8080/ui/index.html`

---

## Token / Autenticação

- Após o login, o token JWT é salvo e utilizado como:
  - `Authorization: Bearer <token>`
- Também é possível colar/usar token manualmente no campo **Token** para testes.

---

## Endpoints utilizados

### Auth
- `POST /auth/register`
- `POST /auth/login`

### Predict
- `POST /api/predict`

### Stats
- `GET /api/stats`

> Observação: os paths acima refletem o dashboard atual. Se forem alterados no backend, ajuste também no `ui/app.js`.

---

## Fluxo de demonstração (demo)

1) Register (criar usuário)
2) Login (obter JWT)
3) Predict (enviar JSON e visualizar resposta)
4) Stats (consultar métricas)
