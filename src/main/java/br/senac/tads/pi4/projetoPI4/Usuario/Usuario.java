package br.senac.tads.pi4.projetoPI4.Usuario;

import java.io.Serializable;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private int codUsuario;
    private String nome;
    private String email;
    private String senha;
    private String estado;
    private String tipo;
    private String img;

    public Usuario(int codUsuario, String nome, String email, String senha, String estado, String tipo){
        this.codUsuario = codUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.estado = estado;
        this.tipo = tipo;
    }

    public Usuario(){

    }

    public static long getSerialVersionUID() {

        return serialVersionUID;
    }

    public int getCodUsuario() {

        return codUsuario;
    }

    public void setCodUsuario(int codUsuario) {

        this.codUsuario = codUsuario;
    }

    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {

        this.nome = nome;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getSenha() {

        return senha;
    }

    public void setSenha(String senha) {

        this.senha = senha;
    }

    public String getEstado() {

        return estado;
    }

    public void setEstado(String estado) {

        this.estado = estado;
    }

    public String getTipo() {

        return tipo;
    }

    public void setTipo(String tipo) {

        this.tipo = tipo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
