package siga;

import java.util.List;

/**
 * Serviço de aplicação responsável pelas regras de negócio da matrícula.
 *
 * Refatorado para atender aos princípios SOLID:
 * - SRP (Responsabilidade Única): lida exclusivamente com as regras de negócio acadêmicas,
 *   sem misturar comandos SQL ou detalhes de infraestrutura de persistência.
 * - DIP (Inversão de Dependência): depende da abstração AlunoDAO, recebida
 *   via injeção de dependência pelo construtor, desacoplando-se de implementações concretas.
 */
public class ServicoMatricula {

    private final AlunoDAO alunoDAO;

    public ServicoMatricula(AlunoDAO alunoDAO) {
        this.alunoDAO = alunoDAO;
    }

    public void matricular(Aluno aluno) {
        // Regra de negócio
        if (aluno.getMedia() < 0 || aluno.getMedia() > 10) {
            throw new IllegalArgumentException("Média inválida: " + aluno.getMedia());
        }

        // Persistência delegada ao DAO
        alunoDAO.inserir(aluno);
    }

    public void gerarRelatorio() {
        List<Aluno> alunos = alunoDAO.listarTodos();

        System.out.println("=== Relatório de Alunos ===");
        for (Aluno aluno : alunos) {
            System.out.println(aluno);
        }
    }
}
