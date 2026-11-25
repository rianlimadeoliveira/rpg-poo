package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.VidaInvalidaException;

public class Inimigo extends Personagem {

    private int xpRecompensa;
    private int ouro;

    public Inimigo(String nome, int vidaMaxima, int ataque, int xpRecompensa, int ouro)
            throws VidaInvalidaException {
        super(nome, vidaMaxima, ataque);
        this.xpRecompensa = Math.max(0, xpRecompensa);
        this.ouro = Math.max(0, ouro);
    }

    @Override
    public int calcularDano() {
        return ataque;
    }

    public int getXpRecompensa() {
        return xpRecompensa;
    }

    public int getOuro() {
        return ouro;
    }
}
