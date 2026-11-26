package br.ufrr.dcc.projeto.repositorio;

import br.ufrr.dcc.projeto.excecoes.SemEspacoException;
import br.ufrr.dcc.projeto.modelo.Heroi;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Repositorio em memoria, com limite de herois, para cadastro e consulta pelo nome. */
public class HeroiRepository {
    private static final int LIMITE_PADRAO = 50;

    private final List<Heroi> herois;
    private final int limite;

    public HeroiRepository() {
        this(LIMITE_PADRAO);
    }

    public HeroiRepository(int limite) {
        if (limite <= 0) {
            throw new IllegalArgumentException("O limite deve ser maior que zero.");
        }
        this.limite = limite;
        this.herois = new ArrayList<>();
    }

    /** Salva o heroi; se ja existir um com o mesmo nome, ele e substituido. */
    public synchronized void salvar(Heroi h) throws SemEspacoException {
        if (h == null) {
            throw new IllegalArgumentException("O heroi nao pode ser nulo.");
        }
        Heroi existente = buscar(h.getNome());
        if (existente != null) {
            herois.set(herois.indexOf(existente), h);
            return;
        }
        if (herois.size() >= limite) {
            throw new SemEspacoException("Limite de herois cadastrados atingido.", limite);
        }
        herois.add(h);
    }

    /** Busca ignorando maiusculas/minusculas e espacos nas pontas; retorna null se nao achar. */
    public synchronized Heroi buscar(String nome) {
        if (nome == null) {
            return null;
        }
        for (Heroi h : herois) {
            if (h.getNome().equalsIgnoreCase(nome.trim())) {
                return h;
            }
        }
        return null;
    }

    public synchronized boolean existe(String nome) {
        return buscar(nome) != null;
    }

    public synchronized List<Heroi> listarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(herois));
    }

    public synchronized int quantidade() {
        return herois.size();
    }

    public int getLimite() {
        return limite;
    }
}
