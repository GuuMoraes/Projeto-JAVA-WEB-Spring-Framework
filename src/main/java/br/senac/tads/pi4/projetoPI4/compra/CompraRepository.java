package br.senac.tads.pi4.projetoPI4.compra;

import br.senac.tads.pi4.projetoPI4.curso.Curso;

import java.util.List;

public interface CompraRepository {

    Compra salvar(Compra compra);

    List<Compra> listar();

    List<Compra> listarN(String txtBuscar);

    void mudarEstado(int id, String estado);

    Compra findById(Integer id);

}
