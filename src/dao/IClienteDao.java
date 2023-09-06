package dao;

import domain.Cliente;

import java.util.List;

public interface IClienteDao {
    Integer cadastrar(Cliente cliente) throws Exception;
    Cliente buscar(String codigo) throws Exception;
    Integer excluir(Cliente cliente) throws Exception;
    Integer atualizar(Cliente cliente) throws Exception;
    List<Cliente> buscarTodos() throws Exception;
}
