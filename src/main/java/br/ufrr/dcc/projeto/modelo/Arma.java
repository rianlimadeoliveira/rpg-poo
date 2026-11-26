package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.ItemInvalidoException;
import br.ufrr.dcc.projeto.excecoes.RPGException;

/** Arma consumida ao usar: aumenta permanentemente o ataque do heroi. */
public class Arma extends Item {
    private final int dano;

    public Arma(String nome, int valor, int dano) throws ItemInvalidoException {
        super(nome, valor);
        if (dano <= 0) {
            throw new ItemInvalidoException("O dano da arma deve ser positivo.", nome);
        }
        this.dano = dano;
    }

    public int getDano() {
        return dano;
    }

    @Override
    public void usar(Heroi h) throws RPGException {
        if (h == null) {
            throw new ItemInvalidoException("Nao e possivel usar a arma em um heroi nulo.", nome);
        }
        h.aumentarAtaque(dano);
    }
}
