[![Continuous Integration with Github](https://github.com/lwbaleeiro/alaska-insurance/actions/workflows/docker-image.yml/badge.svg)](https://github.com/lwbaleeiro/alaska-insurance/actions/workflows/docker-image.yml)

# About:
Alaska Insurance is an application for insurance purchasing.

## Requirements:
Java 17 ou superior \
Docker 4.4.4 ou superior

## Comandos Docker
- **docker compose up -d**
    - *Através do terminal, dentro da pasta onde contem o docker-compose-yml monta as imagens que subiram no [DockerHub](https://hub.docker.com/u/lwbaleeiro).*
- **docker ps**
    - *Mostras os containers rodando atualmente.*
- **docker images**
    - *Mostra as imagens baixadas localmente.*
- **docker logs {id}**
    - *Mostra o log da aplicação escolhida de conforme o ID.*
- **docker compose down**
    - *Derruba todos os containers ativos no momento.*
- **docker run -p {port}:{port} {name-image}:{version-image}**
    - *Executa uma única imagem e sobe o container.*