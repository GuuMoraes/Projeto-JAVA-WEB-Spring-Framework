package br.senac.tads.pi4.projetoPI4.curso;

import java.io.Serializable;
import java.util.List;

public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;

    private int codCurso;
    //@NotBlank
    //@Size(min = 3, max = 280)
    private String nome;
    //@NotBlank
    //@Size(min = 0, max = 2000)
    private String nomeExtenso;
    //@NotBlank
    //@Size(min = 0)
    private String descricao;
    //@NotBlank
    //@Size(max = 2000)
    private String conteudo;
    //@NotBlank
    //@Size(max = 1000)
    private String requisitos;
    //@NotBlank
    private String estado;
    //@NotBlank
    //@Min(0)
    //@Max(5)
    private int qntEstrelas =0;
    //@NotBlank
    //@Min(1)
    private int qntVagas =0;
    //@NotNull
    //@Digits(integer = 9, fraction = 2)
    private double preco = 0;

    private String imgPri;

    private List<CursoImagens> imgs;

    public Curso(){

    }

    public Curso(int codCurso, String nome, String nomeExtenso, int qntEstrelas, double preco, String descricao, String conteudo, String requisitos, String estado, int qntVagas){
        this.setCodCurso(codCurso);
        this.setNome(nome);
        this.setNomeExtenso(nomeExtenso);
        this.setQntEstrelas(qntEstrelas);
        this.setPreco(preco);
        this.setDescricao(descricao);
        this.setConteudo(conteudo);
        this.setRequisitos(requisitos);
        this.setEstado(estado);
        this.setQntVagas(qntVagas);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getCodCurso() {

        return codCurso;
    }

    public void setCodCurso(int codCurso) {

        this.codCurso = codCurso;
    }

    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {

        this.nome = nome;
    }

    public String getNomeExtenso() {

        return nomeExtenso;
    }

    public void setNomeExtenso(String nomeExtenso) {

        this.nomeExtenso = nomeExtenso;
    }

    public int getQntEstrelas() {

        return qntEstrelas;
    }

    public void setQntEstrelas(int qntEstrelas) {

        this.qntEstrelas = qntEstrelas;
    }

    public double getPreco() {

        return preco;
    }

    public void setPreco(double preco) {

        this.preco = preco;
    }

    public String getDescricao() {

        return descricao;
    }

    public void setDescricao(String descricao) {

        this.descricao = descricao;
    }

    public String getConteudo() {

        return conteudo;
    }

    public void setConteudo(String conteudo) {

        this.conteudo = conteudo;
    }

    public String getRequisitos() {

        return requisitos;
    }

    public void setRequisitos(String requisitos) {

        this.requisitos = requisitos;
    }

    public String getEstado() {

        return estado;
    }

    public void setEstado(String estado) {

        this.estado = estado;
    }

    public int getQntVagas() {

        return qntVagas;
    }

    public void setQntVagas(int qntVagas) {

        this.qntVagas = qntVagas;
    }

    public List<CursoImagens> getImgs() {
        return imgs;
    }

    public void setImgs(List<CursoImagens> imgs) {
        this.imgs = imgs;
    }

    public String getImgPri() {
        return imgPri;
    }

    public void setImgPri(String imgPri) {
        this.imgPri = imgPri;
    }
}
