package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.ItemInvalidoException;
import br.ufrr.dcc.projeto.excecoes.RPGException;

/** Pocao que recupera uma quantidade limitada de pontos de vida. */
public class Pocao extends Item {
    private final int cura;
    public Pocao(String nome, String descricao, int valor, int cura) throws ItemInvalidoException {
        super(nome, descricao, valor);
        if (cura <= 0) throw new ItemInvalidoException("A quantidade de cura deve ser positiva.");
        this.cura = cura;
    }
    public int getCura() { return cura; }
    @Override public void usar(Heroi heroi) throws RPGException {
        if (heroi == null) throw new ItemInvalidoException("Nao e possivel usar a pocao em um heroi nulo.");
        if (!heroi.estaVivo()) throw new ItemInvalidoException("Um heroi derrotado nao pode usar pocao.");
        heroi.recuperarVida(cura);
    }
}
