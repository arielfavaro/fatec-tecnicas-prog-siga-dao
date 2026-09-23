# Diagnóstico - Etapa 1

## 1. SQL Misturado à Regra de Negócio

- **Onde está:** Em `ServicoMatricula.java`, nos métodos `matricular` (linhas 43-48) e `gerarRelatorio` (linhas 52-53).
- **O problema:** A classe de serviço, que deveria conter apenas regras de negócio acadêmicas, monta e executa comandos SQL diretamente (`INSERT INTO aluno...` e `SELECT nome, matricula, media FROM aluno`).
  - **Acoplamento tecnológico:** A regra de negócio conhece detalhes de infraestrutura e persistência, como sintaxe SQL, nomes de tabelas e de colunas.

---

## 2. Violação do SRP (Single Responsibility Principle — Princípio da Responsabilidade Única)

- **Onde está:** Em `ServicoMatricula.java`, principalmente no método `matricular`.
- **O problema:** A classe acumula mais de uma responsabilidade, possuindo múltiplos motivos para mudar:
  - **Motivo 1 (Regra de Negócio):** Mudanças nos critérios acadêmicos de matrícula (ex.: alterar intervalo válido de notas/médias ou adicionar novas validações cadastrais).
  - **Motivo 2 (Persistência):** Mudanças no esquema de banco de dados (ex.: renomear colunas, alterar tipos de dados ou migrar dialetos SQL).
---

## 3. Violação do DIP (Dependency Inversion Principle — Princípio da Inversão de Dependência)

- **Onde está:** Em `ServicoMatricula.java`, nas chamadas diretas aos métodos estáticos de `BancoSimulado` (`BancoSimulado.executar(...)` e `BancoSimulado.consultar(...)`).
- **O problema:** Um módulo de alto nível (`ServicoMatricula`) depende diretamente de uma implementação concreta de baixo nível (`BancoSimulado`), em vez de depender de uma abstração.
  - **Acoplamento rígido:** Não é possível trocar o mecanismo de persistência (por exemplo, migrar do banco simulado para um banco relacional real ou para armazenamento em memória) sem modificar o código da regra de negócio.
  - **Testabilidade comprometida:** Para testar uma simples regra de validação (`if (aluno.getMedia() < 0)`), exige-se que a infraestrutura de banco esteja presente e ativa. Não é possível executar testes unitários puros e isolados sem acionar a persistência.

---

## 4. Duplicação de Código (Violação do DRY — Don't Repeat Yourself)

- **Onde está:** Em `ServicoMatricula.java`, nos métodos `matricular` e `gerarRelatorio`.
- **O problema:** O conhecimento sobre a estrutura da tabela `aluno` e o mapeamento de seus campos (`nome`, `matricula`, `media`) aparecem duplicados e espalhados em diferentes métodos da classe de serviço.
  - **Inconsistência e retrabalho:** Caso um novo campo seja adicionado à entidade `Aluno` (como `curso` ou `email`), será necessário alterar manualmente múltiplos comandos SQL espalhados no serviço, gerando risco de esquecimento e inconsistências.
