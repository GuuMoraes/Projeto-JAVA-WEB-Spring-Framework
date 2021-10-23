package br.senac.tads.pi4.projetoPI4.compra;

import java.util.List;

public interface ItensCompraRepository  {

    ItensCompra salvar(ItensCompra itensCompra);

    List<ItensCompra> listarI(int id);
}
