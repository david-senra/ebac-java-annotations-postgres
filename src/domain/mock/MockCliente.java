package domain.mock;

import domain.Cliente;
import domain.IPersistente;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MockCliente extends Cliente implements IPersistente {

    private final Map<Long, Cliente> listClientesCadastrados = new HashMap<>();
    private final Map<Long, Cliente> listClientesNaoCadastrados = new HashMap<>();

    public MockCliente() {
        Cliente cliente01 = new Cliente(1L, "David", "Senra", "088.361.746-39",
                "(31) 98495-9703", "Rua Cláudio Manoel", "Belo Horizonte", "MG");
        listClientesCadastrados.put(cliente01.getId(), cliente01);
        Cliente cliente02 = new Cliente(2L, "Joana", "Fonseca", "142.754.223-56",
                "(35) 99537-1364", "Avenida Brasil", "Juiz de Fora", "MG");
        listClientesCadastrados.put(cliente02.getId(), cliente02);
        Cliente cliente03 = new Cliente(3L, "Ana", "Costa", "100.344.546-85",
                "(21) 98654-1523", "Rua do Ouro", "Pato de Minas", "MG");
        listClientesCadastrados.put(cliente03.getId(), cliente03);
        Cliente cliente04 = new Cliente(4L, "Ludovico", "Santiago", "200.155.985-43",
                "(31) 99730-1212", "Rua Tom Fagundes", "São Paulo", "SP");
        listClientesCadastrados.put(cliente04.getId(), cliente04);
        Cliente cliente05 = new Cliente(5L, "Ricardo", "Framil", "450.950.235-65",
                "(31) 97553-8315", "Rua Zimbábue", "Rio de Janeiro", "RJ");
        listClientesCadastrados.put(cliente05.getId(), cliente05);
        Cliente cliente06 = new Cliente(6L, "Zé", "das Couves", "443.573.285-34",
                "(31) 97412-6970", "Rua Antônio Aleixo", "Fortaleza", "CE");
        listClientesNaoCadastrados.put(cliente06.getId(), cliente06);
        Cliente cliente07 = new Cliente(7L, "Verônica", "Villas", "155.982.110-99",
                "(81) 99429-5015", "Rua Vigário da Costa", "Recife", "PE");
        listClientesNaoCadastrados.put(cliente07.getId(), cliente07);
        Cliente cliente08 = new Cliente(8L, "Paula", "Nobumoto", "185.246.247-75",
                "(13) 97548-5566", "Avenida do Galo", "Vitória", "ES");
        listClientesNaoCadastrados.put(cliente08.getId(), cliente08);
        Cliente cliente09 = new Cliente(9L, "Bruno", "Vilaça", "150.973.888-45",
                "(11) 98226-9840", "Viela da Felicidade", "Campinas", "SP");
        listClientesNaoCadastrados.put(cliente09.getId(), cliente09);
        Cliente cliente10 = new Cliente(10L, "Eduardo", "Stubbert", "586.254.164-23",
                "(34) 99774-1427", "Rua Santos Américo", "Nova York", "NY");
        listClientesNaoCadastrados.put(cliente10.getId(), cliente10);
    }

    public Cliente getMockCliente() {
        int indiceAtual = new Random().nextInt(4);
        indiceAtual += 1;
        return listClientesCadastrados.get((long) indiceAtual);
    }

    public Cliente getMockClienteNaoCadastrado() {
        long indiceAtual = new Random().nextLong(4);
        indiceAtual += 6;
        return listClientesNaoCadastrados.get(indiceAtual);
    }

    public Cliente getMockClienteIdRepetido(Cliente cliente) {
        return new Cliente(cliente.getId(),
                "Romario",
                "Faria",
                cliente.getCpf(),
                "(21) 98536-3592",
                "Avenida Nossa Senhora de Copacabana",
                "Angra dos Reis",
                "RO");
    }

    public Cliente getMockClienteIdDiferenteTudoRepetido(Cliente cliente) {
        return new Cliente(11L,
                cliente.getNome(),
                cliente.getSobrenome(),
                "555.555.555-55",
                cliente.getTelefone(),
                cliente.getEndereco(),
                cliente.getCidade(),
                cliente.getEstado());
    }

}
