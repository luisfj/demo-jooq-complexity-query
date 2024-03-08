<h1 align="center">
  Projeto de Estudos JooQ CTE
</h1>

<p align="center">
 <img src="https://img.shields.io/badge/-LinkedIn-02569B?logo=linkedin&logoColor=white&style=fot-the-badge" alt="luis-fernando-johann" />
</p>

- Este projeto é uma reprodução de uma solução desenvolvida pelo arquiteto do projeto em que atuo, desenvolvi eh um esqueleto parecido para fins de estudos, em uma estrutura minimalista e sem considerações arquiteturais ou padronizações.
- O foco do mesmo é simplesmente demonstrar a utilização de várias queries utilizando CTE(Common Table Expression), uma alternativa a subqueries, views e funções definidas pelo usuário.
- Não levei em consideração nenhuma normalização.
- O projeto foi desenvolvido em VSCode, fazendo uso de Dev Containers, sendo fácil subir, somente é necessário seguir os passos abaixo.
- O servidor está configurado para rodar na porta 8181, e somente existe um endpoint: [/produto](http://localhost:8181/produto)

## Tecnologias

- Java 21
- Container rodando o banco de dados Postgres, com nome do container "postgresdb"
 
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Jooq - spring-boot-starter-jooq](https://www.jooq.org/)
- [Flyway](https://flywaydb.org/)
- [Postgresql](https://www.postgresql.org/)

## Como Executar

### VSCode (Dev Containers)

- Instalar a extensão [Dev Containers](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers)
- Clonar repositório git
- Disponibilizar as credenciais git pelo ssh-agent.
    Seguir [tutorial](https://code.visualstudio.com/remote/advancedcontainers/sharing-git-credentials)
- No VSCode apertar F1 e selecionar a opção "Dev Containers: Open Folder in Container..."
- Selecionar a pasta raiz do projeto
- Após abrir o projeto pelo Dev Containers, abrir o arquivo "DemoJooqComplexityQueryApplication.java" e executar/debugar o mesmo


## Acessando a API

A API poderá ser acessada em [localhost:8181/produto](http://localhost:8181/produto).
