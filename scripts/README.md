# Scripts — S.E.N.T.I.N.E.L.A.

Scripts de banco de dados e infraestrutura do projeto de monitoramento de desastres naturais.

---

## script-bd.sql — DDL do Banco Oracle

Script completo de criação do banco de dados com todas as tabelas, sequences e insert obrigatório do satélite NASA FIRMS.

**Como executar:**

1. Abra o SQL Developer e conecte no Oracle FIAP
2. Abra o arquivo `script-bd.sql`
3. Execute na ordem — o script já está ordenado corretamente:
    - CREATE TABLE (8 tabelas)
    - CREATE SEQUENCE (7 sequences)
    - INSERT do satélite VIIRS_SNPP_NRT (obrigatório para o fluxo FIRMS)
    - COMMIT

> ⚠️ O INSERT do satélite `VIIRS_SNPP_NRT` é obrigatório. Sem ele a importação automática da NASA FIRMS falha com erro de FK nula.

---

## script-infra.sh — Provisionamento Azure

Script de provisionamento da infraestrutura no Azure via CLI. Cria o Resource Group, App Service Plan e Web App configurado para Java 21.

**Pré-requisitos:**

- Azure CLI instalado
- Conta Azure ativa com subscrição disponível

**Como executar:**

```bash
# 1. Autenticar no Azure
az login

# 2. Dar permissão de execução ao script
chmod +x script-infra.sh

# 3. Executar
./script-infra.sh
```

Após a execução o Web App estará disponível em:
https://app-sentinela-api-RM560475.azurewebsites.net

> ⚠️ As variáveis sensíveis (DB_PASS, RABBITMQ_PASSWORD, RABBITMQ_USERNAME, MAP_KEY) devem ser preenchidas manualmente no Azure Portal em **Configurações → Variáveis de ambiente** antes do primeiro deploy.

---

## Equipe

| RM | Nome | Responsabilidade |
|---|---|---|
| RM553043 | Daniel Kendi | Oracle DDL + Seeds + PL/SQL |
| RM560179 | Lucas da Ressurreição | API Java + ML Python |
| RM560560 | Jonas Kimio | Mobile React Native |
| RM560475 | Marcos Vinicius | DevOps + Azure Pipeline |

**FIAP — Análise e Desenvolvimento de Sistemas — 2026/1**