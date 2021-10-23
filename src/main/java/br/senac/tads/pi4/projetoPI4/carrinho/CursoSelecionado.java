package br.senac.tads.pi4.projetoPI4.carrinho;

import br.senac.tads.pi4.projetoPI4.curso.Curso;

public class CursoSelecionado {

    private Curso curso;
    private String userAgent;

    public CursoSelecionado() {

    }

    public CursoSelecionado(Curso curso, String userAgent) {
        this.setCurso(curso);
        this.setUserAgent(userAgent);
    }


    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
