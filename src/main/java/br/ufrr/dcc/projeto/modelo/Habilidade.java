package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.RPGException;

/** Contrato comum para habilidades que um heroi pode aprender. */
public interface Habilidade {
    String getNome();
    void executar(Heroi usuario, Personagem alvo) throws RPGException;
}
