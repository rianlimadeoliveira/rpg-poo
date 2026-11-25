package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.ItemInvalidoException;
import br.ufrr.dcc.projeto.excecoes.RPGException;

/** Arma consumida ao usar: aumenta permanentemente o ataque do heroi. */
public class Arma extends Item {
    private final int bonusAtaque;
    public Arma(String nome, String descricao, int valor, int bonusAtaque) throws ItemInvalidoException {
        super(nome, descricao, valor);
        if (bonusAtaque <= 0) throw new ItemInvalidoException("O bonus de ataque deve ser positivo.");
        this.bonusAtaque = bonusAtaque;
    }
    public int getBonusAtaque() { return bonusAtaque; }
    @Override public void usar(Heroi heroi) throws RPGException {
        if (heroi == null) throw new ItemInvalidoException("Nao e possivel usar a arma em um heroi nulo.");
        heroi.aumentarAtaque(bonusAtaque);
    }
}
