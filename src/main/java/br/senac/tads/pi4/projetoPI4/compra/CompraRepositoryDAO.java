package br.senac.tads.pi4.projetoPI4.compra;

import br.senac.tads.pi4.projetoPI4.BD.ConexaoDB;
import br.senac.tads.pi4.projetoPI4.cliente.Cliente;
import br.senac.tads.pi4.projetoPI4.cliente.ClienteRepositoryDAO;
import br.senac.tads.pi4.projetoPI4.curso.Curso;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompraRepositoryDAO implements CompraRepository {

    @Override
    public Compra salvar(Compra compra) {
        Connection conexao;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = ConexaoDB.conectar();
            instrucaoSQL = conexao.prepareStatement("insert into compra (datacompra, formapagamento, valortotal, cliente_codusuario, estado) values(?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);

            instrucaoSQL.setDate(1, Date.valueOf(LocalDate.now()));
            instrucaoSQL.setString(2, compra.getFormaPag());
            instrucaoSQL.setDouble(3, compra.getValorTotal());
            instrucaoSQL.setInt(4, compra.getCliente().getCodCliente());
            instrucaoSQL.setString(5, compra.getEstado());
            int linhasAfetadas = instrucaoSQL.executeUpdate();
            if (linhasAfetadas > 0) {
                ResultSet generatedKeys = instrucaoSQL.getGeneratedKeys();
                if (generatedKeys.next()) {
                    compra.setCodCompra(generatedKeys.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return compra;
    }

    @Override
    public List<Compra> listar() {
        List<Compra> lista = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = ConexaoDB.conectar().createStatement();
            rs = st.executeQuery("SELECT * FROM compra");

            while (rs.next()) {
                Compra compra = new Compra();

                compra.setCodCompra(rs.getInt(1));
                compra.setDataCompra(rs.getString(2));
                compra.setFormaPag(rs.getString(3));
                compra.setValorTotal(rs.getDouble(4));
                Cliente cliente = new ClienteRepositoryDAO().findById(rs.getInt(5));
                compra.setCliente(cliente);
                compra.setEstado(rs.getString(6));
                lista.add(compra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Compra> listarN(String txtBuscar) {
        List<Compra> lista = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        String buscar = '%' + txtBuscar + '%';

        try {
            st = ConexaoDB.conectar().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery("SELECT * FROM compra where codcompra LIKE '" + buscar + "'");

            while (rs.next()) {
                Compra compra = new Compra();

                compra.setCodCompra(rs.getInt(1));
                compra.setDataCompra(rs.getString(2));
                compra.setFormaPag(rs.getString(3));
                compra.setValorTotal(rs.getDouble(4));
                Cliente cliente = new ClienteRepositoryDAO().findById(rs.getInt(5));
                compra.setCliente(cliente);
                compra.setEstado(rs.getString(6));
                lista.add(compra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void mudarEstado(int id, String estado) {
        Connection conexao;
        PreparedStatement instrucaoSQL = null;
        Compra compra = findById(id);
        if (!compra.getEstado().equals(estado)) {
            try {
                conexao = ConexaoDB.conectar();
                instrucaoSQL = conexao.prepareStatement("update compra set estado=? where codcompra='" + id + "'");
                if (!(compra.getEstado().equals("Pagamento Rejeitado") || compra.getEstado().equals("Download Disponivel")) &&
                        !(compra.getEstado().equals("Pagamento Aprovado") && (estado.equals("Processando Pagamento") || estado.equals("Pagamento Rejeitado"))) &&
                        !(compra.getEstado().equals("Processando Pagamento") && estado.equals("Download Disponivel")) &&
                        !(compra.getEstado().equals("Aguardando Pagamento") && (estado.equals("Pagamento Aprovado") || estado.equals("Pagamento Rejeitado") || estado.equals("Download Disponivel")))) {
                    instrucaoSQL.setString(1, estado);
                    instrucaoSQL.execute();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Compra findById(Integer id) {
        Statement st = null;
        ResultSet rs = null;

        try {
            st = ConexaoDB.conectar().createStatement();
            rs = st.executeQuery("SELECT * FROM compra where codcompra ='" + id + "'");

            while (rs.next()) {
                Compra compra = new Compra();

                compra.setCodCompra(rs.getInt(1));
                compra.setDataCompra(rs.getString(2));
                compra.setFormaPag(rs.getString(3));
                compra.setValorTotal(rs.getDouble(4));
                Cliente cliente = new ClienteRepositoryDAO().findById(rs.getInt(5));
                compra.setCliente(cliente);
                compra.setEstado(rs.getString(6));
                return compra;
            }
        } catch (ClassNotFoundException | SQLException ex) {


        }
        return null;
    }
}
