package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.VidaInvalidaException;

public class Mago extends Heroi {

    private int mana;

    public Mago(String nome, int vidaMaxima, int ataque, int mana) throws VidaInvalidaException {
        super(nome, vidaMaxima, ataque);
        if (mana < 0) throw new IllegalArgumentException("O atributo mana nao pode ser negativo.");
        this.mana = mana;
    }

    @Override
    public int calcularDano() {
        return super.calcularDano() + mana / 5;
    }

    public int getMana() {
        return mana;
    }
}
