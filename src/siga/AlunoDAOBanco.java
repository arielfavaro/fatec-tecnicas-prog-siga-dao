package siga;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação de AlunoDAO que utiliza o BancoSimulado.
 *
 * Encapsula todos os comandos e sintaxes SQL nesta camada de persistência.
 */
public class AlunoDAOBanco implements AlunoDAO {

    private final List<Aluno> alunos = new ArrayList<>();

    @Override
    public void inserir(Aluno aluno) {
        if (aluno == null) return;

        String sql = "INSERT INTO aluno (nome, matricula, media) VALUES ('"
                + aluno.getNome() + "', '"
                + aluno.getMatricula() + "', "
                + aluno.getMedia() + ")";

        BancoSimulado.executar(sql, aluno.toString());

        alunos.add(aluno);
    }

    @Override
    public Aluno buscarPorMatricula(String matricula) {
        for (Aluno a : alunos) {
            if (a.getMatricula().equals(matricula)) {
                return a;
            }
        }

        return null;
    }

    @Override
    public List<Aluno> listarTodos() {
        String sql = "SELECT nome, matricula, media FROM aluno";

        BancoSimulado.consultar(sql);

        return new ArrayList<>(alunos);
    }

    @Override
    public void atualizar(Aluno aluno) {
        if (aluno == null) return;

        String sql = "UPDATE aluno SET nome = '" + aluno.getNome()
                + "', media = " + aluno.getMedia()
                + " WHERE matricula = '" + aluno.getMatricula() + "'";

        BancoSimulado.executar(sql, aluno.toString());

        for (int i = 0; i < alunos.size(); i++) {
            if (alunos.get(i).getMatricula().equals(aluno.getMatricula())) {
                alunos.set(i, aluno);

                return;
            }
        }
    }

    @Override
    public void remover(String matricula) {
        String sql = "DELETE FROM aluno WHERE matricula = '" + matricula + "'";

        BancoSimulado.executar(sql, matricula);

        for (int i = 0; i < alunos.size(); i++) {
            if (alunos.get(i).getMatricula().equals(matricula)) {
                alunos.remove(i);
                return;
            }
        }
    }
}
