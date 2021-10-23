package br.senac.tads.pi4.projetoPI4.compra;

import br.senac.tads.pi4.projetoPI4.BD.ConexaoDB;
import br.senac.tads.pi4.projetoPI4.curso.Curso;
import br.senac.tads.pi4.projetoPI4.curso.CursoRepositoryDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItensCompraRepositoryDAO implements ItensCompraRepository {
    @Override
    public ItensCompra salvar(ItensCompra itensCompra) {
        Connection conexao;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = ConexaoDB.conectar();
            instrucaoSQL = conexao.prepareStatement("insert into itenscompra (valorUnitario, curso_codcurso, compra_codcompra) values(?,?,?);", Statement.RETURN_GENERATED_KEYS);

            instrucaoSQL.setDouble(1, itensCompra.getValorUnitario());
            instrucaoSQL.setInt(2, itensCompra.getCurso().getCodCurso());
            instrucaoSQL.setInt(3, itensCompra.getCompra().getCodCompra());
            instrucaoSQL.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itensCompra;
    }

    @Override
    public List<ItensCompra> listarI(int id) {
        List<ItensCompra> lista = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = ConexaoDB.conectar().createStatement();
            rs = st.executeQuery("SELECT * FROM itenscompra where compra_codcompra = '" + id + "'");

            while (rs.next()) {
                ItensCompra itensCompra = new ItensCompra();

                itensCompra.setCodItens(rs.getInt(1));
                itensCompra.setValorUnitario(rs.getDouble(2));
                Curso curso = new CursoRepositoryDAO().findById(rs.getInt(3));
                itensCompra.setCurso(curso);
                Compra compra = new CompraRepositoryDAO().findById(id);
                itensCompra.setCompra(compra);
                lista.add(itensCompra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
