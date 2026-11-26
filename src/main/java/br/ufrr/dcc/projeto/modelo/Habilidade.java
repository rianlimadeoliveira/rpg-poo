package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.RPGException;

/** Contrato comum para habilidades que um heroi pode aprender. */
public interface Habilidade {
    String getNome();

    int getCusto();

    void usar(Personagem alvo) throws RPGException;
}
