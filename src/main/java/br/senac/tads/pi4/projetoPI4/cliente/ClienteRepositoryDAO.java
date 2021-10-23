package br.senac.tads.pi4.projetoPI4.cliente;

import br.senac.tads.pi4.projetoPI4.BD.ConexaoDB;
import br.senac.tads.pi4.projetoPI4.Usuario.Usuario;
import br.senac.tads.pi4.projetoPI4.compra.Compra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepositoryDAO implements ClienteRepository {
    @Override
    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = ConexaoDB.conectar().createStatement();
            rs = st.executeQuery("SELECT * FROM usuarios where tipo='cliente'");

            while (rs.next()) {
                Cliente cliente = new Cliente();

                cliente.setCodCliente(rs.getInt(1));
                cliente.setNome(rs.getString(2));
                cliente.setCpf(rs.getString(3));
                cliente.setNasc(rs.getString(4));
                cliente.setEmail(rs.getString(5));
                cliente.setSenha(rs.getString(6));
                cliente.setCep(rs.getString(8));
                cliente.setRua(rs.getString(9));
                cliente.setNumero(rs.getString(10));
                cliente.setComplemento(rs.getString(11));
                cliente.setBairro(rs.getString(12));
                cliente.setCidade(rs.getString(13));
                cliente.setEstado(rs.getString(14));
                lista.add(cliente);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;

    }

    @Override
    public Cliente cadastrar(Cliente c) {
        Connection conexao;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = ConexaoDB.conectar();
            instrucaoSQL = conexao.prepareStatement("insert into usuarios (nome, cpf, nasc, email, senha, cep, endereco, numero, complemento, bairro, cidade, estado) values(?,?,?,?,?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);

            instrucaoSQL.setString(1, c.getNome());
            instrucaoSQL.setString(2, c.getCpf());
            instrucaoSQL.setString(3, c.getNasc());
            instrucaoSQL.setString(4, c.getEmail());
            instrucaoSQL.setString(5, c.getSenha());
            instrucaoSQL.setString(6, c.getCep());
            instrucaoSQL.setString(7, c.getRua());
            instrucaoSQL.setString(8, c.getNumero());
            instrucaoSQL.setString(9, c.getComplemento());
            instrucaoSQL.setString(10, c.getBairro());
            instrucaoSQL.setString(11, c.getCidade());
            instrucaoSQL.setString(12, c.getEstado());
            instrucaoSQL.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public Cliente findById(Integer id) {
        Statement st = null;
        ResultSet rs = null;

        try {
            st = ConexaoDB.conectar().createStatement();
            rs = st.executeQuery("SELECT * FROM usuarios where codusuario ='" + id + "'");

            while (rs.next()) {
                Cliente cliente = new Cliente();

                cliente.setCodCliente(rs.getInt(1));
                cliente.setNome(rs.getString(2));
                cliente.setCpf(rs.getString(3));
                cliente.setNasc(rs.getString(4));
                cliente.setEmail(rs.getString(5));
                cliente.setSenha(rs.getString(6));
                cliente.setCep(rs.getString(8));
                cliente.setRua(rs.getString(9));
                cliente.setNumero(rs.getString(10));
                cliente.setComplemento(rs.getString(11));
                cliente.setBairro(rs.getString(12));
                cliente.setCidade(rs.getString(13));
                cliente.setEstado(rs.getString(14));

                return cliente;
            }
        } catch (ClassNotFoundException | SQLException ex) {


        }
        return null;
    }

    @Override
    public List<Cliente> listarcpf(String cpf) {
        List<Cliente> lista = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        String buscar = '%' + cpf + '%';

        try {
            st = ConexaoDB.conectar().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery("SELECT * FROM usuarios where cpf LIKE '" + buscar + "'");

            while (rs.next()) {
                Cliente cliente = new Cliente();

                cliente.setCodCliente(rs.getInt(1));
                cliente.setNome(rs.getString(2));
                cliente.setCpf(rs.getString(3));
                cliente.setNasc(rs.getString(4));
                cliente.setEmail(rs.getString(5));
                cliente.setSenha(rs.getString(6));
                cliente.setCep(rs.getString(8));
                cliente.setRua(rs.getString(9));
                cliente.setNumero(rs.getString(10));
                cliente.setComplemento(rs.getString(11));
                cliente.setBairro(rs.getString(12));
                cliente.setCidade(rs.getString(13));
                cliente.setEstado(rs.getString(14));
                lista.add(cliente);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Compra> listarPedidos(Integer id) {
        List<Compra> lista = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = ConexaoDB.conectar().createStatement();
            rs = st.executeQuery("SELECT * FROM compra where cliente_codusuario= '" + id + "' order by codcompra desc ");

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

    public Cliente buscarEmail(String email) {
        Statement st = null;
        ResultSet rs = null;

        try {
            st = ConexaoDB.conectar().createStatement();
            rs = st.executeQuery("SELECT * FROM usuarios where email = '" + email + "'");

            while (rs.next()) {
                Cliente cliente = new Cliente();

                cliente.setCodCliente(rs.getInt(1));
                cliente.setNome(rs.getString(2));
                cliente.setCpf(rs.getString(3));
                cliente.setNasc(rs.getString(4));
                cliente.setEmail(rs.getString(5));
                cliente.setSenha(rs.getString(6));
                cliente.setCep(rs.getString(8));
                cliente.setRua(rs.getString(9));
                cliente.setNumero(rs.getString(10));
                cliente.setComplemento(rs.getString(11));
                cliente.setBairro(rs.getString(12));
                cliente.setCidade(rs.getString(13));
                cliente.setEstado(rs.getString(14));
                cliente.setTipo(rs.getString(15));
                return cliente;
            }
        } catch (ClassNotFoundException | SQLException ex) {
        }
        return null;
    }

    public void editar(Cliente cliente) {
        Connection conexao;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = ConexaoDB.conectar();
            instrucaoSQL = conexao.prepareStatement("update usuarios set cep=?, endereco=?, numero=?, complemento=?, bairro=?, cidade=?, estado=? where codusuario=?");

            instrucaoSQL.setString(1, cliente.getCep());
            instrucaoSQL.setString(2, cliente.getRua());
            instrucaoSQL.setString(3, cliente.getNumero());
            instrucaoSQL.setString(4, cliente.getComplemento());
            instrucaoSQL.setString(5, cliente.getBairro());
            instrucaoSQL.setString(6, cliente.getCidade());
            instrucaoSQL.setString(7, cliente.getEstado());
            instrucaoSQL.setInt(8, cliente.getCodCliente());
            instrucaoSQL.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
