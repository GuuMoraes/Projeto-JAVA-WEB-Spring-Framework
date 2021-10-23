package br.senac.tads.pi4.projetoPI4.curso;

import java.io.Serializable;

public class CursoImagens implements Serializable {
    private static final long serialVersionUID = 1L;

    private int codImagens;
    private int principal;
    private String caminho;
    private int cursoId;

    public CursoImagens(){

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getCodImagens() {
        return codImagens;
    }

    public void setCodImagens(int codImagens) {
        this.codImagens = codImagens;
    }

    public int getPrincipal() {
        return principal;
    }

    public void setPrincipal(int principal) {
        this.principal = principal;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }
}
