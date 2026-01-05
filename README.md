# 📊 Churn Insight — API, ML & Secure Integration

Este projeto demonstra a **integração entre um serviço de Machine Learning e uma API backend**, com foco em **arquitetura, comunicação entre serviços, segurança, observabilidade e separação de responsabilidades**.

O objetivo é apresentar, de forma **didática e progressiva**, como um modelo de predição pode ser disponibilizado como serviço, consumido por uma API Java, protegido por autenticação e validado por meio de métricas e uma interface de apoio.

---

## 🧠 Conceito do Projeto

O projeto é dividido em **dois serviços independentes**, organizados em um **monorepo**:

* **ML Service (Python / FastAPI)**
  Responsável por carregar o pipeline/modelo treinado e executar a inferência.

* **API Backend (Java / Spring Boot)**
  Responsável por:
  * validar dados de entrada
  * aplicar regras de negócio
  * autenticar usuários (JWT)
  * encaminhar requisições ao serviço de ML
  * retornar respostas padronizadas ao cliente

Essa separação permite:
* desacoplamento entre backend e machine learning
* evolução independente dos serviços
* integração via HTTP, como em ambientes produtivos
* maior controle de erros, métricas e segurança

---

## 🏗️ Arquitetura Geral

```text
Client
  ↓
Spring Boot API (Java)
  ↓
ML Service (Python / FastAPI)
  ↓
Prediction Result
```

* A API Java **não executa lógica de Machine Learning**
* Toda a inferência ocorre no serviço Python
* A API atua como camada de **orquestração, segurança e observabilidade**

---

## 📁 Estrutura do Repositório

```text
churn-insight/
├── api-java/                 # API Backend (Spring Boot)
├── ml-service-python/        # Serviço de ML (FastAPI)
└── docs/                     # Documentação técnica detalhada
```

---

## 🚀 Features implementadas (evolução progressiva)

As funcionalidades foram implementadas **por etapas**, cada uma isolada em branches específicas, seguindo boas práticas de versionamento e evolução incremental.

---

### 1️⃣ Tratamento global de erros e validações

* Contrato de erro padronizado
* Validação de campos (DTOs)
* Identificação de requisições via `requestId`
* Separação clara entre erros de validação, JSON inválido e falhas internas

📄 Documentação completa:
👉 `docs/api-error-examples.md`

---

### 2️⃣ Endpoint de métricas (Stats)

* Endpoint dedicado para métricas internas da API
* Contabilização de:

  * requisições
  * predições bem-sucedidas
  * erros de validação
  * erros de JSON
  * falhas do serviço de ML
* Métricas mantidas em memória, sem acoplamento aos controllers

📄 Documentação completa:
👉 `docs/stats-endpoint.md`

---

### 3️⃣ Autenticação e autorização (JWT)

* Cadastro de usuários (Register)
* Login com geração de **JWT**
* Proteção de rotas sensíveis
* Uso de `Authorization: Bearer <token>`
* Senhas armazenadas de forma criptografada

📄 Documentação completa:
👉 `docs/auth-endpoints.md`

---

### 4️⃣ UI Dashboard (Auth + Predict + Stats)

Dashboard web simples (HTML/CSS/JS), servido diretamente pelo Spring Boot, com foco em **visualização e testes manuais** da API.

Permite:

* Register de usuários
* Login e uso do token JWT
* Execução de predições
* Consulta das métricas (Stats)

🌐 Acesso local:
`http://localhost:8080/ui/index.html`

📄 Documentação:
👉 `docs/ui-dashboard.md`

> O dashboard é propositalmente simples, sem frameworks, e serve como ferramenta de demonstração e apoio durante desenvolvimento e apresentações.

---

## 🔮 Predict — Exemplo rápido (Request / Response)

A API recebe os dados do cliente, valida o payload e encaminha a requisição ao **ML Service (Python)** para execução da inferência.

### 📥 Request (POST)

`/api/predict`

```bash
curl -i -X POST http://localhost:8080/api/predict \
  -H "Content-Type: application/json" \
  -d '{
    "gender": "FEMALE",
    "seniorCitizen": true,
    "partner": true,
    "dependents": true,
    "contractMonths": 72,
    "phoneService": true,
    "multipleLines": "NO",
    "internetService": "DSL",
    "onlineSecurity": "NO",
    "onlineBackup": "NO",
    "deviceProtection": "NO",
    "techSupport": "NO",
    "streamingTV": "NO",
    "streamingMovies": "NO",
    "contractType": "MONTH_TO_MONTH",
    "paperlessBilling": true,
    "paymentMethod": "ELECTRONIC_CHECK",
    "monthlyCharges": 89.99,
    "totalCharges": 1000.00
  }'
```

### 📤 Response (200 OK)

```json
{
  "id": 1,
  "prediction": "No Churn",
  "probability": 0.2947
}
```

📌 **Interpretação**
Cliente com menor probabilidade de churn, conforme a inferência retornada pelo modelo.

> Para exemplos completos, incluindo erros `400`, JSON inválido e `500`, consulte:
> 👉 `docs/api-error-examples.md`

---

## ▶️ Execução Local

### ML Service (Python)

```bash
cd ml-service-python
pip install -r requirements.txt
uvicorn app.main:app --port 8000
```

### API Backend (Java)

```bash
cd api-java
./mvnw spring-boot:run
```

### UI Dashboard

```text
http://localhost:8080/ui/index.html
```

---

## 🎯 O que este projeto demonstra

* Integração real entre Backend e Machine Learning
* Comunicação entre serviços via HTTP
* Autenticação JWT em APIs REST
* Tratamento global de erros
* Observabilidade básica (stats)
* Organização em **monorepo**
* Evolução incremental por feature branches
* Separação clara de responsabilidades

---

## 🧪 Contexto

Projeto desenvolvido no contexto de um **Hackathon**, com foco em aprendizado,
colaboração e aplicação de boas práticas de engenharia de software.

---

## 📚 Documentação Técnica

Toda a documentação detalhada está disponível na pasta `docs/`:

* `api-error-examples.md`
* `stats-endpoint.md`
* `auth-endpoints.md`
* `ui-dashboard.md`


