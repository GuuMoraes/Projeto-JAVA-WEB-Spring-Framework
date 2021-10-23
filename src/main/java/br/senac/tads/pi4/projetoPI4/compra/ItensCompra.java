package br.senac.tads.pi4.projetoPI4.compra;

import br.senac.tads.pi4.projetoPI4.curso.Curso;

import java.io.Serializable;

public class ItensCompra implements Serializable {
    private static final long serialVersionUID = 1L;

    private int codItens;
    private Curso curso;
    private Compra compra;
    private Double valorUnitario = 0.0;

    public ItensCompra() {

    }

    public int getCodItens() {

        return codItens;
    }

    public void setCodItens(int codItens) {

        this.codItens = codItens;
    }

    public Curso getCurso() {

        return curso;
    }

    public void setCurso(Curso curso) {

        this.curso = curso;
    }

    public Compra getCompra() {

        return compra;
    }

    public void setCompra(Compra compra) {

        this.compra = compra;
    }

    public Double getValorUnitario() {

        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {

        this.valorUnitario = valorUnitario;
    }

}
