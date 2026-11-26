package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.ItemInvalidoException;
import br.ufrr.dcc.projeto.excecoes.SemEspacoException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Inventario com capacidade fixa; agrega os itens do heroi. */
public class Inventario {
    private final List<Item> itens;
    private final int capacidade;

    public Inventario(int capacidade) {
        if (capacidade <= 0) {
            throw new IllegalArgumentException("A capacidade deve ser maior que zero.");
        }
        this.capacidade = capacidade;
        this.itens = new ArrayList<>();
    }

    public synchronized void adicionar(Item i) throws ItemInvalidoException, SemEspacoException {
        if (i == null) {
            throw new ItemInvalidoException("O item nao pode ser nulo.", null);
        }
        if (itens.size() >= capacidade) {
            throw new SemEspacoException("O inventario esta cheio.", capacidade);
        }
        itens.add(i);
    }

    public synchronized void remover(Item i) throws ItemInvalidoException {
        if (i == null || !itens.remove(i)) {
            throw new ItemInvalidoException("O item nao esta no inventario.", i == null ? null : i.getNome());
        }
    }

    public synchronized List<Item> getItens() {
        return Collections.unmodifiableList(new ArrayList<>(itens));
    }

    public int getCapacidade() {
        return capacidade;
    }
}
