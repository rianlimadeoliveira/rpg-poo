package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.RPGException;
import br.ufrr.dcc.projeto.excecoes.ItemInvalidoException;

/** Representa um objeto que pode ser guardado no inventario e usado por um heroi. */
public abstract class Item {
    private final String nome;
    private final String descricao;
    private final int valor;

    protected Item(String nome, String descricao, int valor) throws ItemInvalidoException {
        if (nome == null || nome.trim().isEmpty()) throw new ItemInvalidoException("O nome do item nao pode ser vazio.");
        if (valor < 0) throw new ItemInvalidoException("O valor do item nao pode ser negativo.");
        this.nome = nome.trim();
        this.descricao = descricao == null ? "" : descricao.trim();
        this.valor = valor;
    }
    public abstract void usar(Heroi heroi) throws RPGException;
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public int getValor() { return valor; }
    @Override public String toString() { return nome + (descricao.isEmpty() ? "" : " - " + descricao); }
}
