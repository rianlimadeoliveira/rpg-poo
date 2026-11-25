package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.VidaInvalidaException;

public class Arqueiro extends Heroi {

    private int precisao;

    public Arqueiro(String nome, int vidaMaxima, int ataque, int precisao) throws VidaInvalidaException {
        super(nome, vidaMaxima, ataque);
        this.precisao = precisao;
    }

    @Override
    public int calcularDano() {
        return super.calcularDano() + precisao;
    }

    public int getPrecisao() {
        return precisao;
    }
}
