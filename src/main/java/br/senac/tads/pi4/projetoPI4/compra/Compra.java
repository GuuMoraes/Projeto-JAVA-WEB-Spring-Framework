package br.senac.tads.pi4.projetoPI4.compra;

import br.senac.tads.pi4.projetoPI4.cliente.Cliente;

import java.io.Serializable;

public class Compra implements Serializable {
    private static final long serialVersionUID = 1L;

    private int codCompra;
    private Cliente cliente;
    private String dataCompra;
    private String formaPag;
    private Double valorTotal =0.;
    private String estado;

    public Compra(int codCompra, Cliente cliente, String formaPag, Double valorTotal) {
        this.codCompra = codCompra;
        this.cliente = cliente;
        this.formaPag = formaPag;
        this.valorTotal = valorTotal;
    }

    public Compra() {

    }

    public int getCodCompra() {

        return codCompra;
    }

    public void setCodCompra(int codCompra) {

        this.codCompra = codCompra;
    }

    public Cliente getCliente() {

        return cliente;
    }

    public void setCliente(Cliente cliente) {

        this.cliente = cliente;
    }

    public String getDataCompra() {

        return dataCompra;
    }

    public void setDataCompra(String dataCompra) {

        this.dataCompra = dataCompra;
    }

    public String getFormaPag() {

        return formaPag;
    }

    public void setFormaPag(String formaPag) {

        this.formaPag = formaPag;
    }

    public Double getValorTotal() {

        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {

        this.valorTotal = valorTotal;
    }

    public String getEstado() {

        return estado;
    }

    public void setEstado(String estado) {

        this.estado = estado;
    }
}
