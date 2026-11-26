package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.ItemInvalidoException;
import br.ufrr.dcc.projeto.excecoes.RPGException;

/** Representa um objeto que pode ser guardado no inventario e usado por um heroi. */
public abstract class Item {
    protected String nome;
    protected int valor;

    protected Item(String nome, int valor) throws ItemInvalidoException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ItemInvalidoException("O nome do item nao pode ser vazio.", nome);
        }
        if (valor < 0) {
            throw new ItemInvalidoException("O valor do item nao pode ser negativo.", nome);
        }
        this.nome = nome.trim();
        this.valor = valor;
    }

    public abstract void usar(Heroi h) throws RPGException;

    public String getNome() {
        return nome;
    }

    public int getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return nome;
    }
}
