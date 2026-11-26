package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.RPGException;
import br.ufrr.dcc.projeto.excecoes.VidaInvalidaException;

/**
 * Confronto entre um heroi e um inimigo. Implementa Runnable para poder ser
 * executada em uma Thread: run() executa turnos ate um dos lados ser derrotado.
 */
public class Batalha implements Runnable {
    private final Heroi heroi;
    private final Inimigo inimigo;
    private volatile boolean emAndamento;

    public Batalha(Heroi heroi, Inimigo inimigo) throws RPGException {
        if (heroi == null || inimigo == null) {
            throw new RPGException("Heroi e inimigo sao obrigatorios para a batalha.");
        }
        if (!heroi.estaVivo() || !inimigo.estaVivo()) {
            throw new RPGException("Os participantes precisam estar vivos para iniciar a batalha.");
        }
        this.heroi = heroi;
        this.inimigo = inimigo;
    }

    @Override
    public void run() {
        emAndamento = true;
        try {
            while (heroi.estaVivo() && inimigo.estaVivo()) {
                executarTurno();
            }
        } catch (VidaInvalidaException e) {
            // Nao ocorre: os atributos sao validados e o dano calculado nunca e negativo.
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            emAndamento = false;
        }
        if (heroi.estaVivo()) {
            heroi.ganharXp(inimigo.getXpRecompensa());
        }
    }

    /** Heroi ataca primeiro; se o inimigo sobreviver, contra-ataca. */
    private void executarTurno() throws VidaInvalidaException {
        heroi.atacar(inimigo);
        if (inimigo.estaVivo()) {
            inimigo.atacar(heroi);
        }
    }

    public boolean isEmAndamento() {
        return emAndamento;
    }

    public Heroi getHeroi() {
        return heroi;
    }

    public Inimigo getInimigo() {
        return inimigo;
    }
}
