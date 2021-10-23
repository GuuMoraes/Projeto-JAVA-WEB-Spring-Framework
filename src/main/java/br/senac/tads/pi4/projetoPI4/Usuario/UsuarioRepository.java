package br.senac.tads.pi4.projetoPI4.Usuario;

import br.senac.tads.pi4.projetoPI4.curso.Curso;

import java.util.List;

public interface UsuarioRepository {
    List<Usuario> listar();

    List<Usuario> buscarNome(String nome);

    Usuario cadastrar(Usuario u);

    Usuario findById(Integer id);

    void onoff(String estado, Integer id);

    void editar(Usuario u);
}
