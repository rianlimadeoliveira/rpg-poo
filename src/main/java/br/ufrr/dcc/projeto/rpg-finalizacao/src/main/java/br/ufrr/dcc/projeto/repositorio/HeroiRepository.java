package br.ufrr.dcc.projeto.repositorio;

import br.ufrr.dcc.projeto.modelo.Heroi;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Repositorio em memoria para cadastro e consulta de herois pelo nome. */
public class HeroiRepository {
    private final Map<String, Heroi> herois = new LinkedHashMap<>();
    private String chave(String nome) { return nome == null ? "" : nome.trim().toLowerCase(); }
    public synchronized void salvar(Heroi heroi) {
        if (heroi == null) throw new IllegalArgumentException("O heroi nao pode ser nulo.");
        herois.put(chave(heroi.getNome()), heroi);
    }
    public synchronized Optional<Heroi> buscarPorNome(String nome) { return Optional.ofNullable(herois.get(chave(nome))); }
    public synchronized Heroi buscar(String nome) { return herois.get(chave(nome)); }
    public synchronized boolean remover(String nome) { return herois.remove(chave(nome)) != null; }
    public synchronized List<Heroi> listarTodos() { return Collections.unmodifiableList(new ArrayList<>(herois.values())); }
    public synchronized int quantidade() { return herois.size(); }
    public synchronized boolean existe(String nome) { return herois.containsKey(chave(nome)); }
}
