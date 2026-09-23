package siga;

import java.util.List;

/**
 * Interface de acesso a dados (Data Access Object) para a entidade Aluno.
 *
 * Define o contrato das operações de persistência.
 */
public interface AlunoDAO {

    /**
     * Persiste um novo aluno no meio de armazenamento.
     *
     * @param aluno o aluno a ser inserido
     */
    void inserir(Aluno aluno);

    /**
     * Localiza um aluno a partir do seu identificador de matrícula.
     *
     * @param matricula identificador único do aluno
     * @return o Aluno correspondente, ou null se não for encontrado
     */
    Aluno buscarPorMatricula(String matricula);

    /**
     * Retorna a lista com todos os alunos cadastrados.
     *
     * @return lista de alunos
     */
    List<Aluno> listarTodos();

    /**
     * Atualiza os dados de um aluno previamente cadastrado.
     *
     * @param aluno o aluno com os dados atualizados
     */
    void atualizar(Aluno aluno);

    /**
     * Remove o aluno correspondente à matrícula informada.
     *
     * @param matricula identificador do aluno a ser removido
     */
    void remover(String matricula);
}
