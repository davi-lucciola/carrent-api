# Carrent API

API para gerenciar uma frota de veículos e possibilitar o aluguel desses veículos.

## Documentação

Para acessar a documentação do swagger localmente basta clicar [aqui](http://localhost:8080/swagger-ui/index.html).

Ou acessar o endereço abaixo:
> http://localhost:8080/swagger-ui/index.html

## Tecnologias

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MicrosoftSQLServer](https://img.shields.io/badge/Microsoft%20SQL%20Server-CC2927?style=for-the-badge&logo=microsoft%20sql%20server&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)

## Funcionalidades

### Backend

- [x] Autenticação (Geral)
  - [x] Cadastro de Usuários
  - [x] Login (com JWT)

- [ ] Gerenciamento de Veículos (Admin)
    - [x] CRUD de Veículos
    - [x] CRUD de Tipos de Veículos
    - [ ] Listagem do Historico de Status do Veículo.

- [ ] Aluguel de Veículos (Geral)
  - [x] Solicitar aluguel do veículo (DISPONIVEL -> RESERVADO)
  - [x] Cancelar da propria reserva (RESERVADO -> DISPONIVEL)
  - [ ] Listagem das proprias reservas
  - [ ] Ver códigos no detalhar de uma reserva somente quando ela foi realizada pelo proprio usuário
  - [ ] Job de Cancelamento de Reservas Expiradas

- [ ] Aluguel de Veículos (Admin)
    - [x] Confirmar reserva (RESERVADO -> ALUGADO)
    - [x] Confirmar devolução (ALUGADO -> DISPONIVEL)
    - [x] Cancelar reserva (RESERVADO -> DISPONIVEL)
    - [ ] Listagem das reservas
