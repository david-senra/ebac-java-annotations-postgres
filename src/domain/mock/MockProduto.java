package domain.mock;

import domain.IPersistente;
import domain.Produto;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MockProduto extends Produto implements IPersistente {

    private final Map<Long, Produto> listProdutosCadastrados = new HashMap<>();
    private final Map<Long, Produto> listProdutosNaoCadastrados = new HashMap<>();

    public MockProduto() {
        Produto produto01 = new Produto(1L, "Copo", "Um simpático copo lagoinha", 2.30);
        listProdutosCadastrados.put(produto01.getId(), produto01);
        Produto produto02 = new Produto(2L, "Refrigerante", "Necessário para matar a sede", 5.50);
        listProdutosCadastrados.put(produto02.getId(), produto02);
        Produto produto03 = new Produto(3L, "Celular", "Não dá pra ficar sem se comunicar", 1200.00);
        listProdutosCadastrados.put(produto03.getId(), produto03);
        Produto produto04 = new Produto(4L, "Vassoura", "Importante para manter a casa limpa", 15.50);
        listProdutosCadastrados.put(produto04.getId(), produto04);
        Produto produto05 = new Produto(5L, "Chinelo", "Você não quer ficar andando descalço em casa, quer?", 22.80);
        listProdutosCadastrados.put(produto05.getId(), produto05);
        Produto produto06 = new Produto(6L, "Mochila", "Você precisa levar suas coisas por aí", 45.10);
        listProdutosNaoCadastrados.put(produto06.getId(), produto06);
        Produto produto07 = new Produto(7L, "Apito", "Não faço ideia o motivo de comprar isso", 22.80);
        listProdutosNaoCadastrados.put(produto07.getId(), produto07);
        Produto produto08 = new Produto(8L, "Venvanse", "Para dar aquela energia no seu dia", 530.25);
        listProdutosNaoCadastrados.put(produto08.getId(), produto08);
        Produto produto09 = new Produto(9L, "Cadeira", "Não fique em pé. Sente-se.", 130.55);
        listProdutosNaoCadastrados.put(produto09.getId(), produto09);
        Produto produto10 = new Produto(10L, "Lâmpada", "Não vamos ficar no escuro, né?", 20.40);
        listProdutosNaoCadastrados.put(produto10.getId(), produto10);
    }

    public Produto getMockProduto() {
        int indiceAtual = new Random().nextInt(4);
        indiceAtual += 1;
        return listProdutosCadastrados.get((long) indiceAtual);
    }

    public Produto getMockProdutoNaoCadastrado() {
        long indiceAtual = new Random().nextLong(4);
        indiceAtual += 6;
        return listProdutosNaoCadastrados.get(indiceAtual);
    }

    public Produto getMockProdutoIdRepetido(Produto produto) {
        return new Produto(produto.getId(),
                "Cone",
                "Um Artefato de trânsito",
                35.60);
    }

    public Produto getMockProdutoIdDiferenteTudoRepetido(Produto produto) {
        return new Produto(11L,
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco());
    }
}
