package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.VidaInvalidaException;

public class Guerreiro extends Heroi {

    private int forca;

    public Guerreiro(String nome, int vidaMaxima, int ataque, int forca) throws VidaInvalidaException {
        super(nome, vidaMaxima, ataque);
        this.forca = forca;
    }

    @Override
    public int calcularDano() {
        return super.calcularDano() + forca;
    }

    public int getForca() {
        return forca;
    }
}
