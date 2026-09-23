package siga;

/**
 * Ponto de entrada do SIGA (código refatorado da Aula 7).
 *
 * Demonstração da Etapa 5:
 * - Troca da implementação do DAO (AlunoDAOBanco e AlunoDAOMemoria) injetada em ServicoMatricula,
 *   sem alterar nenhuma linha da classe de serviço.
 * - Teste da regra de validação da média isolado em memória, sem depender de banco de dados.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== SIGA - Demonstração da Troca de Implementações DAO ===\n");

        // 1. Demonstração com AlunoDAOBanco
        System.out.println("--- 1. Executando com AlunoDAOBanco - SQL isolado no DAO ---");

        AlunoDAO daoBanco = new AlunoDAOBanco();
        ServicoMatricula servicoBanco = new ServicoMatricula(daoBanco);

        servicoBanco.matricular(new Aluno("Maria Silva", "2026001", 8.5));
        servicoBanco.matricular(new Aluno("João Souza",  "2026002", 6.0));
        System.out.println();
        servicoBanco.gerarRelatorio();

        System.out.println("\n------------------------------------------------------------\n");

        // 2. Troca da implementação para AlunoDAOMemoria sem alterar nada na regra de negócio
        System.out.println("--- 2. Trocando para AlunoDAOMemoria ---");
        AlunoDAO daoMemoria = new AlunoDAOMemoria();
        ServicoMatricula servicoMemoria = new ServicoMatricula(daoMemoria);

        servicoMemoria.matricular(new Aluno("Carlos Ferreira", "2026003", 9.0));
        servicoMemoria.matricular(new Aluno("Ana Lima",        "2026004", 7.5));
        System.out.println();
        servicoMemoria.gerarRelatorio();

        // 3. Teste da regra de negócio: média inválida é rejeitada
        System.out.println();
        try {
            servicoMemoria.matricular(new Aluno("Teste Inválido", "2026005", -1));
        } catch (IllegalArgumentException e) {
            System.out.println("Regra de negócio funcionou: " + e.getMessage());
        }

        System.out.println("\nConclusão: ServicoMatricula não contém nenhum comando SQL (SRP)");
        System.out.println("e depende exclusivamente da abstração AlunoDAO (DIP).");
    }
}
