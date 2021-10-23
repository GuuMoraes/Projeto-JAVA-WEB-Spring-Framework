package br.senac.tads.pi4.projetoPI4.curso;
import java.util.List;

public interface CursoRepository {

    List<Curso> listar();

    List<Curso> listarNome(String nome);

    List<Curso> listarAtivos();

    Curso cadastrar(Curso c);

    Curso findById(Integer id);

    void editar(Curso c);

    void onoff(String estado, Integer id);


}
