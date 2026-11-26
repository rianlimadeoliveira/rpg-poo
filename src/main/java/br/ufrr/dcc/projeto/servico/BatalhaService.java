package br.ufrr.dcc.projeto.servico;

import br.ufrr.dcc.projeto.excecoes.RPGException;
import br.ufrr.dcc.projeto.excecoes.SemEspacoException;
import br.ufrr.dcc.projeto.modelo.Batalha;
import br.ufrr.dcc.projeto.modelo.Heroi;
import br.ufrr.dcc.projeto.modelo.Inimigo;
import br.ufrr.dcc.projeto.repositorio.HeroiRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Coordena o cadastro de herois e cria/executa as batalhas. */
public class BatalhaService {
    private final HeroiRepository repo;
    private final List<Batalha> batalhas;

    public BatalhaService() {
        this(new HeroiRepository());
    }

    public BatalhaService(HeroiRepository repo) {
        if (repo == null) {
            throw new IllegalArgumentException("O repositorio nao pode ser nulo.");
        }
        this.repo = repo;
        this.batalhas = new ArrayList<>();
    }

    /** Cria a batalha e a executa em uma Thread, aguardando o seu termino. */
    public void iniciar(Heroi h, Inimigo i) throws RPGException {
        Batalha batalha = new Batalha(h, i);
        batalhas.add(batalha);

        Thread thread = new Thread(batalha, "batalha-" + h.getNome());
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RPGException("A batalha foi interrompida.");
        }
    }

    public void cadastrar(Heroi h) throws SemEspacoException {
        repo.salvar(h);
    }

    public HeroiRepository getRepo() {
        return repo;
    }

    public List<Batalha> getBatalhas() {
        return Collections.unmodifiableList(batalhas);
    }
}
