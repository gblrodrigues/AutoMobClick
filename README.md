# AutoMobClick

AutoMobClick é um plugin para PaperMC desenvolvido em Java 21, utilizando Gradle Kotlin DSL para gerenciamento de dependências.
O projeto foi criado para auxiliar jogadores em modos de jogo persistentes, como RankUP, permitindo a eliminação automática de mobs configurados pelo servidor.
> AutoMobClick não é um mod ou client-side modification. Apenas os administradores do servidor precisam instalar o `.jar` e incluir nos plugins e liberando para os jogadores do servidor.

* [Tecnologias Utilizadas](#tecnologias-utilizadas)
* [Funcionalidades](#funcionalidades)
* [Requisitos](#requisitos)
* [Comandos](#comandos)
* [Configuração](#configuração)
* [Estatísticas](#estatísticas-demo)
* [Como Funciona](#como-funciona)
* [Licença](#licença)
* [Contato](#meu-contato)

## Tecnologias Utilizadas

| Category | Technology | Why |
|----------|------------|-----|
| Language | [![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://www.oracle.com/java/) | Main development language
| API | [![Paper](https://img.shields.io/badge/Minecraft-1.21+-green)](https://papermc.io/) | Version supported Plugin
| Build | [![Gradle Kotlin DSL](https://img.shields.io/badge/Gradle-Kotlin_DSL-02303A?logo=gradle&logoColor=white)](https://gradle.org/) | Organização de dependências do projeto

## Funcionalidades

* Ativar e desativar o AutoClick por comando
* Atacar automaticamente mobs à frente do jogador
* Configuração dos mobs permitidos através do `config.yml`
* Registro de eliminações realizadas nos últimos 5 minutos
* Exibição de estatísticas por meio de um comando dedicado
* Persistência das estatísticas recentes entre reconexões e reinicializações do servidor
* Leve e fácil de configurar

## Requisitos

* Java 21
* PaperMC 1.21+

## Comandos

| Comando | Descrição
|----- |----- |
| `/automobclick`       | Ativa ou desativa o AutoClick
| `/automobclick stats` | Exibe as estatísticas dos últimos 5 minutos

### Aliases

```text
/ac
/autoclick
```

## Configuração

### config.yml
```yaml
allowed-mobs:
  - ZOMBIE
  - IRON_GOLEM
  - BLAZE
  - PIG
  - BREEZE
```

Apenas os mobs listados em `allowed-mobs` podem ser atacados automaticamente e contabilizados nas estatísticas.

## Estatísticas (Demo)

O plugin registra as eliminações realizadas enquanto o AutoClick estiver ativado.
### Demonstração
<img width="609" height="34" alt="image" src="https://github.com/user-attachments/assets/19b42feb-232f-4290-b44a-e36ab3af8a3e" />
<img width="537" height="92" alt="image" src="https://github.com/user-attachments/assets/f3a17dd9-c20f-4eae-a88d-223a2dd771d9" />

> https://github.com/user-attachments/assets/fc164cf4-fbc0-4374-bc50-1627054d68e9

> As estatísticas expiram automaticamente após 5 minutos.

## Como Funciona
1. O jogador ativa o AutoClick.
2. O plugin verifica continuamente entidades à frente do jogador.
3. Se um mob configurado for detectado, ele será atacado automaticamente.
4. As eliminações são registradas e agrupadas por tipo de entidade.
5. O jogador pode visualizar suas estatísticas utilizando `/ac stats`.

## Licença
Este projeto foi desenvolvido para fins de estudo e portfólio.

## Meu Contato

🔗 [LinkedIn](https://www.linkedin.com/in/gblrodrigues/)
