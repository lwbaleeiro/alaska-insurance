## Requerimentos:
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

## Após subir os containers:
**Zipkin**: http://localhost:9411/zipkin/ \
**Rabbitmq**: http://localhost:15672/#/ \
**Eureka**: http://localhost:8761/ \
**Swagger**: http://localhost:8765/swagger-ui.html