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

Microsserviço de pedido, com acompanhamento da entrega:
![image](https://user-images.githubusercontent.com/57732316/179623451-68c974d9-427b-4800-9bfb-8c07a8d521f1.png)

Cadastro de Restaurantes:
![image](https://user-images.githubusercontent.com/57732316/179625138-1479bf9b-1f88-47cf-9bab-8ddf50c19ab6.png)

Autorização pelo Keycloak:

![image](https://user-images.githubusercontent.com/57732316/179623984-d16946be-3298-4d66-acfa-edfb6c41acdb.png)

## Construído com
* [Quarkus](https://quarkus.io/) - Framework Java
* [Keycloak](https://www.keycloak.org/) - Gerenciamento de Acesso e Identidade
* [Maven](https://maven.apache.org/) - Gerenciador de Dependência
* [Prometheus](https://prometheus.io/) - Gerenciador de Métricas
* [Docker](https://www.docker.com/) - Gerenciador de Contêiner
* [Grafana](https://grafana.com/) - Visualizador de Métricas e Desempenho
* [Swagger](https://swagger.io/) - Documentação da API
* [Kibana](https://www.elastic.co/pt/kibana/) - Painel para visualização das requisições
