package dao;

import JDBC.ConnectionFactory;
import domain.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDao implements IProdutoDao {
    @Override
    public Integer cadastrar(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlInsert();
            statement = connection.prepareStatement(sql);
            adicionarParametrosInsert(statement, produto);
            return statement.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, statement, null);
        }
    }

    @Override
    public Produto buscar(String codigo) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Produto produto = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlSelect();
            stm = connection.prepareStatement(sql);
            adicionarParametrosSelect(stm, codigo);
            rs = stm.executeQuery();

            if (rs.next()) {
                produto = new Produto();
                Long code = rs.getLong("CODIGO");
                String nome = rs.getString("NOME");
                String descricao = rs.getString("DESCRICAO");
                Double preco = rs.getDouble("PRECO");
                produto.setId(code);
                produto.setNome(nome);
                produto.setDescricao(descricao);
                produto.setPreco(preco);
            }
        } catch(Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, rs);
        }
        return produto;
    }

    public List<Produto> buscarTodos() throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Produto> list = new ArrayList<>();
        Produto produto;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlSelectAll();
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                produto = new Produto();
                Long code = rs.getLong("CODIGO");
                String nome = rs.getString("NOME");
                String descricao = rs.getString("DESCRICAO");
                Double preco = rs.getDouble("PRECO");
                produto.setId(code);
                produto.setNome(nome);
                produto.setDescricao(descricao);
                produto.setPreco(preco);
                list.add(produto);
            }
        } catch(Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, rs);
        }
        return list;
    }

    @Override
    public Integer atualizar(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlUpdate();
            stm = connection.prepareStatement(sql);
            adicionarParametrosUpdate(stm, produto);
            return stm.executeUpdate();
        } catch(Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public Integer excluir(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlDelete();
            stm = connection.prepareStatement(sql);
            adicionarParametrosDelete(stm, produto);
            return stm.executeUpdate();
        } catch(Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    private String getSqlInsert() {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into tb_produto (id, codigo, nome, descricao, preco) ");
        sb.append("values (nextval('sq_produto'), ?, ?, ?, ?) ");
        return sb.toString();
    }

    private String getSqlSelect() {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from tb_produto ");
        sb.append("where codigo = ?");
        return sb.toString();
    }

    private String getSqlSelectAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from tb_produto ");
        return sb.toString();
    }

    private String getSqlUpdate() {
        StringBuilder sb = new StringBuilder();
        sb.append("update tb_produto ");
        sb.append("set nome = ?, descricao = ?, preco = ? ");
        sb.append("where codigo = ?");
        return sb.toString();
    }

    private String getSqlDelete() {
        StringBuilder sb = new StringBuilder();
        sb.append("delete from tb_produto ");
        sb.append("where codigo = ?");
        return sb.toString();
    }

    private void adicionarParametrosInsert(PreparedStatement statement, Produto produto) throws SQLException {
        statement.setLong(1, produto.getId());
        statement.setString(2, produto.getNome());
        statement.setString(3, produto.getDescricao());
        statement.setDouble(4, produto.getPreco());
    }

    private void adicionarParametrosUpdate(PreparedStatement statement, Produto produto) throws SQLException {
        statement.setString(1, produto.getNome());
        statement.setString(2, produto.getDescricao());
        statement.setDouble(3, produto.getPreco());
        statement.setLong(4, produto.getId());
    }

    private void adicionarParametrosDelete(PreparedStatement statement, Produto produto) throws SQLException {
        statement.setLong(1, produto.getId());
    }

    private void adicionarParametrosSelect(PreparedStatement statement, String codigo) throws SQLException {
        statement.setLong(1, Long.parseLong(codigo));
    }

    private void closeConnection(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void limparProdutos() throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = "truncate table tb_produto";
            stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch(Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, null);
        }
    }
}
