# Quarkus - Ifood

Algumas funcionalidades do Ifood criadas a partir de uma aplicação Quarkus, utilizando o Maven como gerenciador de dependências, o Docker para manutenção de contêineres, Prometheus para gerar as métricas, Grafana para uma melhor visualização das métricas, Keycloak para autenticação e Swagger para a documentação.

### Instalação

```
git clone https://github.com/Gustavobf/quarkus-ifood.git
```
```
docker-compose up
```
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
* [Kibana](https://www.elastic.co/pt/kibana/) - Painel para visualização das requisições
