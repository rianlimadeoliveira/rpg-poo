package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.ItemInvalidoException;
import br.ufrr.dcc.projeto.excecoes.SemEspacoException;
import br.ufrr.dcc.projeto.excecoes.RPGException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Inventario com capacidade fixa e operacoes seguras sobre seus itens. */
public class Inventario {
    private final int capacidade;
    private final List<Item> itens;
    public Inventario(int capacidade) {
        if (capacidade <= 0) throw new IllegalArgumentException("A capacidade deve ser maior que zero.");
        this.capacidade = capacidade; this.itens = new ArrayList<>();
    }
    public synchronized void adicionarItem(Item item) throws ItemInvalidoException, SemEspacoException {
        if (item == null) throw new ItemInvalidoException("O item nao pode ser nulo.");
        if (itens.size() >= capacidade) throw new SemEspacoException("O inventario esta cheio.");
        itens.add(item);
    }
    public synchronized boolean removerItem(Item item) { return itens.remove(item); }
    public synchronized Item removerItem(int indice) throws ItemInvalidoException {
        if (indice < 0 || indice >= itens.size()) throw new ItemInvalidoException("Indice de item invalido.");
        return itens.remove(indice);
    }
    public synchronized Item buscarItem(String nome) {
        if (nome == null) return null;
        for (Item item : itens) if (item.getNome().equalsIgnoreCase(nome.trim())) return item;
        return null;
    }
    public synchronized void usarItem(int indice, Heroi heroi) throws RPGException {
        if (indice < 0 || indice >= itens.size()) throw new ItemInvalidoException("Indice de item invalido.");
        Item item = itens.get(indice); item.usar(heroi); itens.remove(indice);
    }
    public synchronized List<Item> getItens() { return Collections.unmodifiableList(new ArrayList<>(itens)); }
    public int getCapacidade() { return capacidade; }
    public synchronized int getQuantidade() { return itens.size(); }
    public synchronized int getEspacosDisponiveis() { return capacidade - itens.size(); }
    public synchronized boolean estaCheio() { return itens.size() >= capacidade; }
    public synchronized boolean estaVazio() { return itens.isEmpty(); }
}
