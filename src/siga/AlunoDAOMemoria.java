package siga;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementação em memória de AlunoDAO.
 *
 * Armazena os objetos Aluno em um Map interno, utilizando a matrícula como chave.
 */
public class AlunoDAOMemoria implements AlunoDAO {

    private final Map<String, Aluno> alunos = new LinkedHashMap<>();

    @Override
    public void inserir(Aluno aluno) {
        if (aluno != null) {
            alunos.put(aluno.getMatricula(), aluno);
        }
    }

    @Override
    public Aluno buscarPorMatricula(String matricula) {
        return alunos.get(matricula);
    }

    @Override
    public List<Aluno> listarTodos() {
        return new ArrayList<>(alunos.values());
    }

    @Override
    public void atualizar(Aluno aluno) {
        if (aluno != null && alunos.containsKey(aluno.getMatricula())) {
            alunos.put(aluno.getMatricula(), aluno);
        }
    }

    @Override
    public void remover(String matricula) {
        alunos.remove(matricula);
    }
}
