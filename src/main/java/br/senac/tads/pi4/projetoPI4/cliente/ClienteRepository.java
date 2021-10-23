package br.senac.tads.pi4.projetoPI4.cliente;

import br.senac.tads.pi4.projetoPI4.compra.Compra;
import br.senac.tads.pi4.projetoPI4.curso.Curso;

import java.util.List;

public interface ClienteRepository {

    List<Cliente> listar();

    Cliente cadastrar(Cliente c);

    Cliente findById(Integer id);

    List<Cliente> listarcpf(String cpf);

    List<Compra> listarPedidos(Integer id);
}
