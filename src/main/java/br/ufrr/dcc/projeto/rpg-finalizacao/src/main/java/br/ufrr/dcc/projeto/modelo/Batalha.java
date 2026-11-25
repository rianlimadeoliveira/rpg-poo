package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.RPGException;
import java.util.Objects;

/** Estado e regras basicas de um confronto entre um heroi e um inimigo. */
public class Batalha {
    private final Heroi heroi;
    private final Inimigo inimigo;
    private boolean iniciada;
    private boolean finalizada;
    public Batalha(Heroi heroi, Inimigo inimigo) {
        this.heroi = Objects.requireNonNull(heroi, "O heroi e obrigatorio.");
        this.inimigo = Objects.requireNonNull(inimigo, "O inimigo e obrigatorio.");
    }
    public synchronized void iniciar() throws RPGException {
        if (finalizada) throw new RPGException("Esta batalha ja foi finalizada.");
        if (!heroi.estaVivo() || !inimigo.estaVivo()) throw new RPGException("Os participantes precisam estar vivos para iniciar.");
        iniciada = true;
    }
    /** Executa um turno: heroi ataca primeiro; se o inimigo sobreviver, contra-ataca. */
    public synchronized void executarTurno() throws RPGException {
        if (!iniciada) iniciar();
        if (finalizada) throw new RPGException("Esta batalha ja foi finalizada.");
        if (heroi.estaVivo() && inimigo.estaVivo()) heroi.atacar(inimigo);
        if (inimigo.estaVivo() && heroi.estaVivo()) inimigo.atacar(heroi);
        if (!heroi.estaVivo() || !inimigo.estaVivo()) finalizada = true;
    }
    public synchronized boolean isFinalizada() { return finalizada; }
    public synchronized boolean heroiVenceu() { return finalizada && heroi.estaVivo() && !inimigo.estaVivo(); }
    public synchronized boolean inimigoVenceu() { return finalizada && !heroi.estaVivo() && inimigo.estaVivo(); }
    public Heroi getHeroi() { return heroi; }
    public Inimigo getInimigo() { return inimigo; }
}
