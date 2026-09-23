# SIGA — Atividade de Persistência e padrão DAO

**Técnicas de Programação II (TP2) · Aula 7** — CST em Desenvolvimento de Software Multiplataforma · Fatec de Porto Ferreira

Este repositório contém a refatoração do Sistema de Gestão Acadêmica Simplificado (SIGA), aplicando o padrão **DAO (Data Access Object)** e os princípios de design orientado a objetos **SOLID** (**SRP** e **DIP**), eliminando o acoplamento entre a regra de negócio e a tecnologia de persistência.

## Estrutura do projeto

```
siga-dao/
├── docs/
│   └── ANALISE.md             (diagnóstico das violações: SQL, SRP, DIP e DRY)
├── src/
│   └── siga/
│       ├── Aluno.java             (entidade de domínio)
│       ├── AlunoDAO.java          (interface do DAO com operações do domínio)
│       ├── AlunoDAOMemoria.java   (implementação em memória utilizando Map)
│       ├── AlunoDAOBanco.java     (implementação concreta encapsulando BancoSimulado e SQL)
│       ├── BancoSimulado.java     (simulador de banco de dados / driver)
│       ├── ServicoMatricula.java  (classe de negócio desacoplada e sem SQL)
│       └── Main.java              (demonstração da troca de DAOs e testes)
├── Dockerfile
├── docker-compose.yml
└── README.md
```

## Etapas Implementadas

1. **Etapa 1 — Diagnóstico por Escrito:**
   - Registro em [`docs/ANALISE.md`](docs/ANALISE.md) identificando os problemas de SQL na camada de negócio, as violações dos princípios **SRP** (*Single Responsibility Principle*) e **DIP** (*Dependency Inversion Principle*), além da duplicação de mapeamento (**DRY**).

2. **Etapa 2 — Interface `AlunoDAO`:**
   - Criação da interface com o vocabulário exclusivo do domínio: `inserir`, `buscarPorMatricula`, `listarTodos`, `atualizar` e `remover`, sem termos de banco ou tecnologia (`tabela`, `coluna`, `INSERT`).

3. **Etapa 3 — Implementações do DAO:**
   - **`AlunoDAOMemoria`:** Implementação funcional com `Map<String, Aluno>` interno (`LinkedHashMap`), permitindo execução e testes sem banco de dados e preservando a ordem de inserção.
   - **`AlunoDAOBanco`:** Implementação que isola e encapsula as instruções SQL e a interação com `BancoSimulado`.

4. **Etapa 4 — Refatoração de `ServicoMatricula`:**
   - A classe de serviço agora recebe `AlunoDAO` pelo construtor via **Injeção de Dependência**.
   - Todo comando SQL e qualquer dependência direta de tecnologia de banco foram removidos; a classe lida estritamente com validações acadêmicas e orquestração de matrícula.

5. **Etapa 5 — Demonstração no `Main`:**
   - Demonstração da execução com `AlunoDAOBanco` (SQL emitido apenas pelo DAO).
   - Demonstração da troca transparente para `AlunoDAOMemoria` sem alterar uma única linha de `ServicoMatricula`.
   - Teste da validação de negócio (média inválida) em memória, sem necessidade de infraestrutura de banco de dados.

## Como compilar e executar

Pré-requisito: JDK 17 ou superior (`java -version` para verificar).

```bash
# 1. Compilar (a saída vai para a pasta "bin")
javac -d bin src/siga/*.java

# 2. Executar
java -cp bin siga.Main
```

## Critérios de Sucesso Atendidos

- [x] **Zero SQL no Serviço:** A classe `ServicoMatricula` não contém nenhum comando SQL ou referência a driver de persistência.
- [x] **Troca de Implementação:** É possível trocar a persistência (`AlunoDAOBanco` $\leftrightarrow$ `AlunoDAOMemoria`) passando o objeto desejado no construtor de `ServicoMatricula`.
- [x] **Testabilidade Isolada:** A validação de negócio é executada e testada sem a presença ou necessidade de um banco de dados.
