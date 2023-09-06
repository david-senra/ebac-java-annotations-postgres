package Clientes;

import dao.ClienteDao;
import domain.Cliente;
import domain.mock.MockCliente;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.util.PSQLException;

import java.util.List;

public class ClienteTest {

    private ClienteDao clienteDao;
    private Cliente mockCliente;
    private Cliente mockCliente2;
    private Cliente mockClienteRepetido;
    private Cliente mockClienteIdDiferenteTudoRepetido;

    @Before
    public void init() throws Exception {
        clienteDao = new ClienteDao();
        MockCliente mock = new MockCliente();
        mockCliente = mock.getMockCliente();
        mockCliente2 = mock.getMockClienteNaoCadastrado();
        mockClienteRepetido = mock.getMockClienteIdRepetido(mockCliente);
        mockClienteIdDiferenteTudoRepetido = mock.getMockClienteIdDiferenteTudoRepetido(mockCliente);
        clienteDao.limparClientes();
    }

    @Test
    public void cadastrarClienteNovoExpectSuccess() throws Exception {
        int countCadastrar = clienteDao.cadastrar(mockCliente);
        Assert.assertEquals(1, countCadastrar);
        Cliente clienteEncontrado = clienteDao.buscar(mockCliente.getCpf());
        Assert.assertNotNull(clienteEncontrado);
        Assert.assertEquals(clienteEncontrado.getCpf(), mockCliente.getCpf());
        Assert.assertEquals(clienteEncontrado.getNome(), mockCliente.getNome());
        int countDel = clienteDao.excluir(mockCliente);
        Assert.assertEquals(1, countDel);
    }

    @Test(expected = PSQLException.class)
    public void cadastrarClienteJaExistenteExpectError() throws Exception {
        int countCadastrar = clienteDao.cadastrar(mockCliente);
        Assert.assertEquals(1, countCadastrar);
        if (mockCliente.getId().equals(mockClienteRepetido.getId())) {
            clienteDao.cadastrar(mockClienteRepetido);
        }
    }

    @Test
    public void cadastrarClienteIdDiferenteMesmoNomeExpectSuccess() throws Exception {
        int countCadastrar = clienteDao.cadastrar(mockCliente);
        Assert.assertEquals(1, countCadastrar);
        int countCadastrar2 = clienteDao.cadastrar(mockClienteIdDiferenteTudoRepetido);
        Assert.assertEquals(1, countCadastrar2);
        Cliente clienteEncontrado = clienteDao.buscar(mockCliente.getCpf());
        Assert.assertNotNull(clienteEncontrado);
        Assert.assertEquals(clienteEncontrado.getCpf(), mockCliente.getCpf());
        Assert.assertEquals(clienteEncontrado.getNome(), mockCliente.getNome());
        Cliente clienteEncontrado2 = clienteDao.buscar(mockClienteIdDiferenteTudoRepetido.getCpf());
        Assert.assertNotNull(clienteEncontrado2);
        Assert.assertEquals(clienteEncontrado2.getCpf(), mockClienteIdDiferenteTudoRepetido.getCpf());
        Assert.assertEquals(clienteEncontrado2.getNome(), mockClienteIdDiferenteTudoRepetido.getNome());
        int countDel1 = clienteDao.excluir(mockCliente);
        Assert.assertEquals(1, countDel1);
        int countDel2 = clienteDao.excluir(mockClienteIdDiferenteTudoRepetido);
        Assert.assertEquals(1, countDel2);
    }

    @Test
    public void buscarTodosTest() throws Exception {
        int countCadastrar = clienteDao.cadastrar(mockCliente);
        Assert.assertEquals(1, countCadastrar);
        int countCadastrar2 = clienteDao.cadastrar(mockCliente2);
        Assert.assertEquals(1, countCadastrar2);
        List<Cliente> listaClientes = clienteDao.buscarTodos();
        Assert.assertNotNull(listaClientes);
        Assert.assertEquals(2, listaClientes.size());
        listaClientes.forEach(cliente -> Assert.assertTrue(cliente.getNome().equals(mockCliente.getNome())
                                                            || cliente.getNome().equals(mockCliente2.getNome())));
        clienteDao.limparClientes();
        listaClientes = clienteDao.buscarTodos();
        Assert.assertEquals(0, listaClientes.size());
    }

    @Test
    public void atualizarTest() throws Exception {
        int countCadastrar = clienteDao.cadastrar(mockCliente);
        Assert.assertEquals(1, countCadastrar);

        Cliente clienteEncontrado = clienteDao.buscar(mockCliente.getCpf());
        Assert.assertNotNull(clienteEncontrado);
        Assert.assertEquals(clienteEncontrado.getNome(), mockCliente.getNome());
        Assert.assertEquals(clienteEncontrado.getCpf(), mockCliente.getCpf());

        clienteEncontrado.setNome("Mario Bros");
        clienteEncontrado.setSobrenome("Vulgo Luigi");
        clienteEncontrado.setCpf("000-000-000.00");
        int countUpdate = clienteDao.atualizar(clienteEncontrado);
        Assert.assertEquals(1, countUpdate);

        Cliente clienteEncontrado2 = clienteDao.buscar("000-000-000.00");
        Assert.assertNotNull(clienteEncontrado2);
        Assert.assertEquals(clienteEncontrado.getNome(), clienteEncontrado2.getNome());
        Assert.assertEquals(clienteEncontrado.getTelefone(), clienteEncontrado2.getSobrenome());
        Assert.assertEquals(clienteEncontrado.getCpf(), clienteEncontrado2.getCpf());

        clienteDao.limparClientes();
    }
}
