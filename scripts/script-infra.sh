#!/bin/bash
# =============================================================================
# script-infra.sh — Provisionamento Azure CLI para o S.E.N.T.I.N.E.L.A.
# FIAP — ADS 2026/1
# Pré-requisito: Azure CLI instalado e autenticado (az login)
# =============================================================================

# Variáveis — RM do responsável DevOps (Marcos Vinicius RM560475)
RESOURCE_GROUP="rg-sentinela-RM560475"
LOCATION="eastus"
APP_SERVICE_PLAN="plan-sentinela-RM560475"
WEB_APP_BACK="app-sentinela-api-RM560475"

echo "=== 1. Criando o Grupo de Recursos ==="
az group create \
  --name $RESOURCE_GROUP \
  --location $LOCATION

echo "=== 2. Criando o Plano do App Service (Linux) ==="
az appservice plan create \
  --name $APP_SERVICE_PLAN \
  --resource-group $RESOURCE_GROUP \
  --sku F1 \
  --is-linux

echo "=== 3. Criando o Azure Web App para Java 21 (Spring Boot) ==="
az webapp create \
  --name $WEB_APP_BACK \
  --resource-group $RESOURCE_GROUP \
  --plan $APP_SERVICE_PLAN \
  --runtime "JAVA|21-java21"

echo "=== 4. Configurando variáveis de ambiente da aplicação ==="
az webapp config appsettings set \
  --name $WEB_APP_BACK \
  --resource-group $RESOURCE_GROUP \
  --settings \
    DB_URL="jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl" \
    JWT_ISSUER="https://securetoken.google.com/sentinel-api-8b7da" \
    JWT_SECRET="placeholder-nao-usado" \
    RABBITMQ_HOST="guppy-01.rmq6.cloudamqp.com" \
    MAP_KEY=""

echo "=== Infraestrutura provisionada com sucesso! ==="
echo "Web App: https://$WEB_APP_BACK.azurewebsites.net"