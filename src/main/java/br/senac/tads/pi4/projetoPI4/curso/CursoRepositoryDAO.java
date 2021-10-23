package br.senac.tads.pi4.projetoPI4.curso;

import br.senac.tads.pi4.projetoPI4.BD.ConexaoDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CursoRepositoryDAO implements CursoRepository {

    @Override
    public List<Curso> listar() {
        List<Curso> lista = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = ConexaoDB.conectar().createStatement();
            rs = st.executeQuery("SELECT * FROM curso");

            while (rs.next()) {
                Curso curso = new Curso();

                curso.setCodCurso(rs.getInt(1));
                curso.setNome(rs.getString(2));
                curso.setNomeExtenso(rs.getString(3));
                curso.setDescricao(rs.getString(4));
                curso.setConteudo(rs.getString(5));
                curso.setRequisitos(rs.getString(6));
                curso.setQntEstrelas(rs.getInt(7));
                curso.setEstado(rs.getString(8));
                curso.setQntVagas(rs.getInt(9));
                curso.setPreco(rs.getDouble(10));
                lista.add(curso);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Curso> listarNome(String nome) {
        List<Curso> lista = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        String buscar = '%' + nome + '%';

        try {
            st = ConexaoDB.conectar().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery("SELECT * FROM curso where nome LIKE '" + buscar + "'");

            while (rs.next()) {
                Curso curso = new Curso();

                curso.setCodCurso(rs.getInt(1));
                curso.setNome(rs.getString(2));
                curso.setNomeExtenso(rs.getString(3));
                curso.setDescricao(rs.getString(4));
                curso.setConteudo(rs.getString(5));
                curso.setRequisitos(rs.getString(6));
                curso.setQntEstrelas(rs.getInt(7));
                curso.setEstado(rs.getString(8));
                curso.setQntVagas(rs.getInt(9));
                curso.setPreco(rs.getDouble(10));
                curso.setImgPri(new CursoRepositoryDAO().buscaImgP(rs.getInt(1)));
                lista.add(curso);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Curso> listarAtivos() {
        List<Curso> lista = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = ConexaoDB.conectar().createStatement();
            rs = st.executeQuery("SELECT * FROM curso where estado = 'Ativo'");

            while (rs.next()) {
                Curso curso = new Curso();

                curso.setCodCurso(rs.getInt(1));
                curso.setNome(rs.getString(2));
                curso.setNomeExtenso(rs.getString(3));
                curso.setDescricao(rs.getString(4));
                curso.setConteudo(rs.getString(5));
                curso.setRequisitos(rs.getString(6));
                curso.setQntEstrelas(rs.getInt(7));
                curso.setEstado(rs.getString(8));
                curso.setQntVagas(rs.getInt(9));
                curso.setPreco(rs.getDouble(10));
                curso.setImgPri(new CursoRepositoryDAO().buscaImgP(rs.getInt(1)));
                lista.add(curso);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Curso cadastrar(Curso c) {

        Connection conexao;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = ConexaoDB.conectar();
            instrucaoSQL = conexao.prepareStatement("insert into curso (nome, nomeExtenso, descricao, conteudo, requisitos, qtdEstrelas, estado, qtdVagas, valor) values(?,?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);

            instrucaoSQL.setString(1, c.getNome());
            instrucaoSQL.setString(2, c.getNomeExtenso());
            instrucaoSQL.setString(3, c.getDescricao());
            instrucaoSQL.setString(4, c.getConteudo());
            instrucaoSQL.setString(5, c.getRequisitos());
            instrucaoSQL.setInt(6, c.getQntEstrelas());
            instrucaoSQL.setString(7, c.getEstado());
            instrucaoSQL.setInt(8, c.getQntVagas());
            instrucaoSQL.setDouble(9, c.getPreco());

            int linhasAfetadas = instrucaoSQL.executeUpdate();
            if (linhasAfetadas > 0) {
                ResultSet generatedKeys = instrucaoSQL.getGeneratedKeys();
                if (generatedKeys.next()) {
                    c.setCodCurso(generatedKeys.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public Curso findById(Integer id) {
        Statement st = null;
        ResultSet rs = null;

        try {
            st = ConexaoDB.conectar().createStatement();
            rs = st.executeQuery("SELECT * FROM curso where codCurso ='" + id + "'");

            while (rs.next()) {
                Curso curso = new Curso();
                curso.setCodCurso(rs.getInt(1));
                curso.setNome(rs.getString(2));
                curso.setNomeExtenso(rs.getString(3));
                curso.setDescricao(rs.getString(4));
                curso.setConteudo(rs.getString(5));
                curso.setRequisitos(rs.getString(6));
                curso.setQntEstrelas(rs.getInt(7));
                curso.setEstado(rs.getString(8));
                curso.setQntVagas(rs.getInt(9));
                curso.setPreco(rs.getDouble(10));
                curso.setImgPri(new CursoRepositoryDAO().buscaImgP(rs.getInt(1)));
                curso.setImgs(new CursoRepositoryDAO().listarImgs(id));
                return curso;
            }
        } catch (ClassNotFoundException | SQLException ex) {


        }
        return null;
    }

    @Override
    public void editar(Curso c) {
        Connection conexao;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = ConexaoDB.conectar();
            instrucaoSQL = conexao.prepareStatement("update curso set nome=?, nomeExtenso=?, descricao=?, conteudo=?, requisitos=?, qtdEstrelas=?, estado=?, qtdVagas=?, valor=? where codCurso=?");

            instrucaoSQL.setString(1, c.getNome());
            instrucaoSQL.setString(2, c.getNomeExtenso());
            instrucaoSQL.setString(3, c.getDescricao());
            instrucaoSQL.setString(4, c.getConteudo());
            instrucaoSQL.setString(5, c.getRequisitos());
            instrucaoSQL.setInt(6, c.getQntEstrelas());
            instrucaoSQL.setString(7, c.getEstado());
            instrucaoSQL.setInt(8, c.getQntVagas());
            instrucaoSQL.setDouble(9, c.getPreco());
            instrucaoSQL.setInt(10, c.getCodCurso());
            instrucaoSQL.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onoff(String estado, Integer id) {
        Connection conexao;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = ConexaoDB.conectar();
            instrucaoSQL = conexao.prepareStatement("update curso set estado=? where codCurso='" + id + "'");

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


    public void cadastrarImg(CursoImagens img) {
        Connection conexao;
        PreparedStatement instrucaoSQL = null;
        if (listarImgs(img.getCursoId()).isEmpty()) {
            img.setPrincipal(1);
        }
        try {
            conexao = ConexaoDB.conectar();
            instrucaoSQL = conexao.prepareStatement("insert into imagens (principal, caminho, curso_codCurso) values(?,?,?);", Statement.RETURN_GENERATED_KEYS);

            instrucaoSQL.setInt(1, img.getPrincipal());
            instrucaoSQL.setString(2, img.getCaminho());
            instrucaoSQL.setInt(3, img.getCursoId());

            int linhasAfetadas = instrucaoSQL.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CursoImagens> listarImgs(int id) {
        List<CursoImagens> listaImgs = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = ConexaoDB.conectar().createStatement();
            rs = st.executeQuery("SELECT * FROM imagens where curso_codCurso='" + id + "'");

            while (rs.next()) {
                CursoImagens imagem = new CursoImagens();

                imagem.setCodImagens(rs.getInt(1));
                imagem.setPrincipal(rs.getInt(2));
                imagem.setCaminho(rs.getString(3));
                imagem.setCursoId(rs.getInt(4));

                listaImgs.add(imagem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaImgs;
    }

    public void deletarImg(int id) {
        Connection conexao;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = ConexaoDB.conectar();
            instrucaoSQL = conexao.prepareStatement("delete from imagens where codImagens='" + id + "'");

            instrucaoSQL.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletarTudo(int id) {
        Connection conexao;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = ConexaoDB.conectar();
            instrucaoSQL = conexao.prepareStatement("delete from imagens where curso_codCurso='" + id + "'");
            instrucaoSQL.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            conexao = ConexaoDB.conectar();
            instrucaoSQL = conexao.prepareStatement("delete from curso where codCurso='" + id + "'");
            instrucaoSQL.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String buscaImgP(int id) {
        Statement st = null;
        ResultSet rs = null;
        String caminho = null;

        try {
            st = ConexaoDB.conectar().createStatement();
            rs = st.executeQuery("SELECT * FROM imagens where curso_codCurso='" + id + "' and principal= '1'");

            while (rs.next()) {
                caminho = rs.getString(3);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return caminho;
    }

}
