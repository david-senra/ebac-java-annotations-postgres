package dao;

import domain.Produto;

import java.util.List;

public interface IProdutoDao {
    Integer cadastrar(Produto cliente) throws Exception;
    Produto buscar(String codigo) throws Exception;
    Integer excluir(Produto cliente) throws Exception;
    Integer atualizar(Produto cliente) throws Exception;
    List<Produto> buscarTodos() throws Exception;
}
