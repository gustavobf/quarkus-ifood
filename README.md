# Quarkus - Ifood

Algumas funcionalidades do Ifood criadas a partir de uma aplicação Quarkus, utilizando o Maven como gerenciador de dependências, o Docker para manutenção de contêineres, Prometheus para gerar as métricas, Grafana para uma melhor visualização das métricas, Keycloak para autenticação e Swagger para a documentação.

## Sobre Quarkus - Ifood

## Iniciando

### Pré-requisitos

Ter instalado as ferramentas abaixo:

```
Maven
Docker
```

### Instalando

Clone o projeto utilizando:

```
git clone https://github.com/Gustavobf/quarkus-ifood.git
```

## Implantação

Dentro da pasta do projeto, suba o Docker Compose utilizando:

```
docker-compose up
```

Aguarde até que suba completamente todos os contêineres, e então, na subpasta cadastro do projeto, utilize: 

```
.\mvn quarkus:dev
```

## Construído com
* [Quarkus](https://quarkus.io/) - Framework Java
* [Keycloak](https://www.keycloak.org/) - Gerenciamento de Acesso e Identidade
* [Maven](https://maven.apache.org/) - Gerenciador de Dependência
* [Prometheus](https://prometheus.io/) - Gerenciador de Métricas
* [Docker](https://www.docker.com/) - Gerenciador de Contêiner
* [Grafana](https://grafana.com/) - Visualizador de Métricas e Desempenho
* [Swagger](https://swagger.io/) - Documentação da API
