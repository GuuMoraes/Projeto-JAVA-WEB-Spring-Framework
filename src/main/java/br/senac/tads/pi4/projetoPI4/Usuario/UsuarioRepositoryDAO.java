package br.senac.tads.pi4.projetoPI4.Usuario;

import br.senac.tads.pi4.projetoPI4.BD.ConexaoDB;
import br.senac.tads.pi4.projetoPI4.cliente.Cliente;
import br.senac.tads.pi4.projetoPI4.curso.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositoryDAO implements UsuarioRepository{

    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = ConexaoDB.conectar().createStatement();
            rs = st.executeQuery("SELECT * FROM usuarios where tipo='Administrador' or tipo='Suporte'");

            while (rs.next()) {
                Usuario usuario = new Usuario();

                usuario.setCodUsuario(rs.getInt(1));
                usuario.setNome(rs.getString(2));
                usuario.setEmail(rs.getString(5));
                usuario.setSenha(rs.getString(6));
                usuario.setEstado(rs.getString(7));
                usuario.setTipo(rs.getString(15));
                usuario.setImg(rs.getString(16));
                lista.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Usuario> buscarNome(String nome) {
        List<Usuario> lista = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        String buscar = '%' + nome + '%';

        try {
            st = ConexaoDB.conectar().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery("SELECT * FROM usuarios where nome LIKE '" + buscar + "'");

            while (rs.next()) {
                Usuario usuario = new Usuario();

                usuario.setCodUsuario(rs.getInt(1));
                usuario.setNome(rs.getString(2));
                usuario.setEmail(rs.getString(5));
                usuario.setSenha(rs.getString(6));
                usuario.setEstado(rs.getString(7));
                usuario.setTipo(rs.getString(15));
                usuario.setImg(rs.getString(16));
                lista.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;

    }

    @Override
    public Usuario cadastrar(Usuario u) {
        Connection conexao;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao =  ConexaoDB.conectar();
            instrucaoSQL = conexao.prepareStatement("insert into usuarios (nome, email, senha, status, tipo, caminhoImg) values(?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);

            instrucaoSQL.setString(1, u.getNome());
            instrucaoSQL.setString(2, u.getEmail());
            instrucaoSQL.setString(3, u.getSenha());
            instrucaoSQL.setString(4, u.getEstado());
            instrucaoSQL.setString(5, u.getTipo());
            instrucaoSQL.setString(6, u.getImg());
            instrucaoSQL.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public Usuario findById(Integer id) {
        Statement st = null;
        ResultSet rs = null;

        try {
            st = ConexaoDB.conectar().createStatement();
            rs = st.executeQuery("SELECT * FROM usuarios where codusuario ='" + id + "'");

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setCodUsuario(rs.getInt(1));
                usuario.setNome(rs.getString(2));
                usuario.setEmail(rs.getString(5));
                usuario.setSenha(rs.getString(6));
                usuario.setEstado(rs.getString(7));
                usuario.setTipo(rs.getString(15));
                usuario.setImg(rs.getString(16));
                return usuario;
            }
        } catch (ClassNotFoundException | SQLException ex) {


        }
        return null;
    }

    @Override
    public void onoff(String estado, Integer id) {
        Connection conexao;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = ConexaoDB.conectar();
            instrucaoSQL = conexao.prepareStatement("update usuarios set status=? where codusuario='" + id + "'");

            if (estado.equals("Ativo")) {
                instrucaoSQL.setString(1, "Inativo");
            } else {
                instrucaoSQL.setString(1, "Ativo");
            }

            instrucaoSQL.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editar(Usuario u) {
        Connection conexao;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = ConexaoDB.conectar();
            instrucaoSQL = conexao.prepareStatement("update usuarios set nome=?, email=?, senha=?, status=?, tipo=?, caminhoImg=? where codusuario=?");

            instrucaoSQL.setString(1, u.getNome());
            instrucaoSQL.setString(2, u.getEmail());
            instrucaoSQL.setString(3, u.getSenha());
            instrucaoSQL.setString(4, u.getEstado());
            instrucaoSQL.setString(5, u.getTipo());
            instrucaoSQL.setString(6, u.getImg());
            instrucaoSQL.setInt(7, u.getCodUsuario());
            instrucaoSQL.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Usuario buscarEmail(String email){
        Statement st = null;
        ResultSet rs = null;

        try {
            st = ConexaoDB.conectar().createStatement();
            rs = st.executeQuery("SELECT * FROM usuarios where email = '" + email + "'");

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setCodUsuario(rs.getInt(1));
                usuario.setNome(rs.getString(2));
                usuario.setEmail(rs.getString(5));
                usuario.setSenha(rs.getString(6));
                usuario.setEstado(rs.getString(7));
                usuario.setTipo(rs.getString(15));
                usuario.setImg(rs.getString(16));
                return usuario;
            }
        } catch (ClassNotFoundException | SQLException ex) {
        }
        return null;
    }

}
