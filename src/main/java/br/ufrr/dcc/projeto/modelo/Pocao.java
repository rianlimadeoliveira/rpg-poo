package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.ItemInvalidoException;
import br.ufrr.dcc.projeto.excecoes.RPGException;

/** Pocao que recupera uma quantidade limitada de pontos de vida. */
public class Pocao extends Item {
    private final int cura;

    public Pocao(String nome, int valor, int cura) throws ItemInvalidoException {
        super(nome, valor);
        if (cura <= 0) {
            throw new ItemInvalidoException("A quantidade de cura deve ser positiva.", nome);
        }
        this.cura = cura;
    }

    public int getCura() {
        return cura;
    }

    @Override
    public void usar(Heroi h) throws RPGException {
        if (h == null) {
            throw new ItemInvalidoException("Nao e possivel usar a pocao em um heroi nulo.", nome);
        }
        if (!h.estaVivo()) {
            throw new ItemInvalidoException("Um heroi derrotado nao pode usar pocao.", nome);
        }
        h.recuperarVida(cura);
    }
}
