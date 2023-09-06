package Produtos;

import dao.ProdutoDao;
import domain.Produto;
import domain.mock.MockProduto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.util.PSQLException;

import java.util.List;

public class ProdutoTest {

    private ProdutoDao produtoDao;
    private Produto mockProduto;
    private Produto mockProduto2;
    private Produto mockProdutoRepetido;
    private Produto mockProdutoIdDiferenteTudoRepetido;

    @Before
    public void init() throws Exception {
        produtoDao = new ProdutoDao();
        MockProduto mock = new MockProduto();
        mockProduto = mock.getMockProduto();
        mockProduto2 = mock.getMockProdutoNaoCadastrado();
        mockProdutoRepetido = mock.getMockProdutoIdRepetido(mockProduto);
        mockProdutoIdDiferenteTudoRepetido = mock.getMockProdutoIdDiferenteTudoRepetido(mockProduto);
        produtoDao.limparProdutos();
    }

    @Test
    public void cadastrarProdutoNovoExpectSuccess() throws Exception {
        int countCadastrar = produtoDao.cadastrar(mockProduto);
        Assert.assertEquals(1, countCadastrar);
        Produto produtoEncontrado = produtoDao.buscar(mockProduto.getId().toString());
        Assert.assertNotNull(produtoEncontrado);
        Assert.assertEquals(produtoEncontrado.getId(), mockProduto.getId());
        Assert.assertEquals(produtoEncontrado.getNome(), mockProduto.getNome());
        int countDel = produtoDao.excluir(mockProduto);
        Assert.assertEquals(1, countDel);
    }

    @Test(expected = PSQLException.class)
    public void cadastrarProdutoJaExistenteExpectError() throws Exception {
        int countCadastrar = produtoDao.cadastrar(mockProduto);
        Assert.assertEquals(1, countCadastrar);
        if (mockProduto.getId().equals(mockProdutoRepetido.getId())) {
            produtoDao.cadastrar(mockProdutoRepetido);
        }
    }

    @Test
    public void cadastrarProdutoIdDiferenteMesmoNomeExpectSuccess() throws Exception {
        int countCadastrar = produtoDao.cadastrar(mockProduto);
        Assert.assertEquals(1, countCadastrar);
        int countCadastrar2 = produtoDao.cadastrar(mockProdutoIdDiferenteTudoRepetido);
        Assert.assertEquals(1, countCadastrar2);
        Produto produtoEncontrado = produtoDao.buscar(mockProduto.getId().toString());
        Assert.assertNotNull(produtoEncontrado);
        Assert.assertEquals(produtoEncontrado.getId(), mockProduto.getId());
        Assert.assertEquals(produtoEncontrado.getNome(), mockProduto.getNome());
        Produto produtoEncontrado2 = produtoDao.buscar(mockProdutoIdDiferenteTudoRepetido.getId().toString());
        Assert.assertNotNull(produtoEncontrado2);
        Assert.assertEquals(produtoEncontrado2.getId(), mockProdutoIdDiferenteTudoRepetido.getId());
        Assert.assertEquals(produtoEncontrado2.getNome(), mockProdutoIdDiferenteTudoRepetido.getNome());

        int countDel2 = produtoDao.excluir(mockProdutoIdDiferenteTudoRepetido);
        Assert.assertEquals(1, countDel2);
        int countDel1 = produtoDao.excluir(mockProduto);
        Assert.assertEquals(1, countDel1);
    }

    @Test
    public void buscarTodosProdutosTest() throws Exception {
        int countCadastrar = produtoDao.cadastrar(mockProduto);
        Assert.assertEquals(1, countCadastrar);
        int countCadastrar2 = produtoDao.cadastrar(mockProduto2);
        Assert.assertEquals(1, countCadastrar2);
        List<Produto> listaClientes = produtoDao.buscarTodos();
        Assert.assertNotNull(listaClientes);
        Assert.assertEquals(2, listaClientes.size());
        listaClientes.forEach(cliente -> Assert.assertTrue(cliente.getNome().equals(mockProduto.getNome())
                || cliente.getNome().equals(mockProduto2.getNome())));
        produtoDao.limparProdutos();
        listaClientes = produtoDao.buscarTodos();
        Assert.assertEquals(0, listaClientes.size());
    }

    @Test
    public void atualizarProdutoTest() throws Exception {
        int countCadastrar = produtoDao.cadastrar(mockProduto);
        Assert.assertEquals(1, countCadastrar);

        Produto produtoEncontrado = produtoDao.buscar(mockProduto.getId().toString());
        Assert.assertNotNull(produtoEncontrado);
        Assert.assertEquals(produtoEncontrado.getNome(), mockProduto.getNome());
        Assert.assertEquals(produtoEncontrado.getId(), mockProduto.getId());

        produtoEncontrado.setNome("Travesseiro");
        produtoEncontrado.setDescricao("Bom para dormir");
        produtoEncontrado.setPreco(99.90);
        int countUpdate = produtoDao.atualizar(produtoEncontrado);
        Assert.assertEquals(1, countUpdate);

        Produto produtoEncontrado2 = produtoDao.buscar(mockProduto.getId().toString());
        Assert.assertNotNull(produtoEncontrado2);
        Assert.assertEquals(produtoEncontrado.getNome(), produtoEncontrado2.getNome());
        Assert.assertEquals(produtoEncontrado.getDescricao(), produtoEncontrado2.getDescricao());
        Assert.assertEquals(produtoEncontrado.getPreco(), produtoEncontrado2.getPreco());
        Assert.assertEquals(produtoEncontrado.getId(), produtoEncontrado2.getId());

        produtoDao.limparProdutos();
    }
}
