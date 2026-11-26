
# RPG - Programação Orientada a Objetos

**Trabalho Final da disciplina DCC305 — UFRR**

**Equipe:** Rian, Davi e Kewenn.

## Sobre o projeto

Este projeto consiste em um sistema de RPG desenvolvido em Java, com o objetivo de aplicar os principais conceitos da Programação Orientada a Objetos.

O sistema permite cadastrar heróis, consultar personagens, listar heróis registrados e realizar batalhas contra inimigos por meio de uma interface de terminal.

## Funcionalidades

- Criação de heróis das classes Guerreiro e Mago.
- Listagem e consulta de heróis cadastrados.
- Sistema de batalhas contra inimigos.
- Cálculo de dano e controle de vida dos personagens.
- Sistema de experiência e evolução de personagens.
- Inventário, itens, habilidades e missões no modelo do projeto.
- Tratamento de exceções personalizadas.

## Tecnologias utilizadas

- Java 17
- Maven
- Git e GitHub
- NetBeans IDE

## Organização do projeto

O código está dividido nos seguintes pacotes:

- `app`: inicialização e interação com o usuário.
- `modelo`: classes e regras do domínio do RPG.
- `excecoes`: exceções personalizadas.
- `repositorio`: armazenamento e consulta dos heróis.
- `servico`: regras e coordenação das batalhas.

O diagrama de classes UML está disponível em `docs/diagrama-classes.puml`.

## Execução

1. Abra o projeto no NetBeans.
2. Aguarde o Maven carregar as configurações.
3. Execute a classe `JanelaPrincipal`, localizada no pacote `app`.
4. Utilize as opções apresentadas no terminal.

Para verificar a compilação pelo Maven, utilize:

```bash
mvn clean compile
```

## Objetivo acadêmico

Demonstrar a aplicação de encapsulamento, herança, abstração, polimorfismo, organização modular, coleções e tratamento de exceções em Java.
