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

| Categoria | Tecnologia | Motivo |
|----------|------------|-----|
| Linguagem | [![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://www.oracle.com/java/) | Linguagem utilizada no desenvolvimento
| API | [![Paper](https://img.shields.io/badge/Minecraft-1.21+-green)](https://papermc.io/) | Versão mínima para suportar o plugin
| Build | [![Gradle Kotlin DSL](https://img.shields.io/badge/Gradle-Kotlin_DSL-02303A?logo=gradle&logoColor=white)](https://gradle.org/) | Organização de dependências do projeto
| Text API | [![MiniMessage](https://img.shields.io/badge/MiniMessage-Adventure-blue)](https://docs.papermc.io/adventure/minimessage/format/) | Formatação de mensagens configuráveis

## Funcionalidades

* Ativar e desativar o AutoClick por comando
* Atacar automaticamente mobs à frente do jogador
* Configuração dos mobs permitidos através do `config.yml`
* Registro de eliminações realizadas dentro de uma janela de tempo configurável
* Exibição de estatísticas por meio de um comando dedicado
* Exibição da próxima expiração das estatísticas com tempo restante
* Mensagens configuráveis através do `config.yml`
* Persistência das estatísticas recentes entre reconexões e reinicializações do servidor
* Leve e fácil de configurar

## Requisitos

* Java 21
* PaperMC 1.21+

## Comandos

| Comando | Descrição
|----- |----- |
| `/automobclick`       | Ativa ou desativa o AutoClick
| `/automobclick stats` | Exibe as estatísticas dentro da janela configurada

### Aliases

```text
/ac
/autoclick
```

## Configuração

### config.yml
```yaml
settings:
  statistics_window_minutes: 5

allowed-mobs:
  - ZOMBIE
  - IRON_GOLEM
  - BLAZE
  - PIG
  - BREEZE

messages:
  ac_enabled: "<aqua><bold>AUTOCLICK!</bold> <yellow>Você <green>ativou <yellow>o AutoClick."
  ac_disabled: "<aqua><bold>AUTOCLICK!</bold> <yellow>Você <red>desativou <yellow>o AutoClick."
  ac_no_stats: "<aqua><bold>AUTOCLICK!</bold> <red>Você não eliminou nenhum mob nos últimos <yellow>%minutes%m<red>."
  stats_mob_line: "<aqua> ▶ <yellow>%mob%: <green>%amount%"
  ac_stats_header: "<aqua><bold>AUTOCLICK!</bold> <yellow>Você eliminou <green>%total% mobs <yellow>nos últimos <green>%minutes%m<yellow>:"
  ac_next_expiration: "<aqua> 🕛 <gray>Próxima expiração: <red>%mob% <gray>em <red>%time%"
```
| Configuração | Descrição
|----- |----- |
| `settings.statistics_window_minutes` | Define por quantos minutos as eliminações permanecem nas estatísticas
| `allowed-mobs` | Lista de mobs permitidos para o AutoClick funcionar
| `messages` | Mensagens exibidas aos jogadores, com suporte a MiniMessage

> Apenas os mobs listados em **`allowed-mobs`** podem ser atacados automaticamente e contabilizados nas estatísticas.

## Estatísticas (Demo)

O plugin registra as eliminações realizadas enquanto o AutoClick estiver ativado.
### Demonstração
<img width="616" height="48" alt="image" src="https://github.com/user-attachments/assets/d270f32b-cd3a-4305-ab2b-916e37582e01" />
<img width="567" height="190" alt="image" src="https://github.com/user-attachments/assets/9575664c-b11e-4946-9411-b70828eb464e" />

> https://github.com/user-attachments/assets/330dc885-7ca6-4fc5-b647-670a71250976

> As estatísticas expiram automaticamente após o período definido em **`settings.statistics_window_minutes`**.

## Como Funciona
1. O jogador ativa o AutoClick.
2. O plugin verifica continuamente entidades à frente do jogador.
3. Se um mob configurado for detectado, ele será atacado automaticamente.
4. As eliminações são registradas e agrupadas por tipo de entidade.
5. O jogador pode visualizar suas estatísticas utilizando `/ac stats`.
6. O plugin informa qual mob será removido das estatísticas primeiro e quanto tempo falta para sua expiração.

## Licença
Este projeto foi desenvolvido para fins de estudo e portfólio.

## Meu Contato

🔗 [LinkedIn](https://www.linkedin.com/in/gblrodrigues/)
