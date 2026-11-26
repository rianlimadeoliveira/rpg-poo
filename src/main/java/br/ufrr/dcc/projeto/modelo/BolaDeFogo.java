package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.RPGException;

/** Habilidade ofensiva que causa dano direto ao alvo. */
public class BolaDeFogo implements Habilidade {
    private static final int CUSTO = 10;

    private final int dano;

    public BolaDeFogo(int dano) {
        if (dano <= 0) {
            throw new IllegalArgumentException("O dano deve ser positivo.");
        }
        this.dano = dano;
    }

    public BolaDeFogo() {
        this(25);
    }

    @Override
    public String getNome() {
        return "Bola de Fogo";
    }

    @Override
    public int getCusto() {
        return CUSTO;
    }

    public int getDano() {
        return dano;
    }

    @Override
    public void usar(Personagem alvo) throws RPGException {
        if (alvo == null) {
            throw new IllegalArgumentException("O alvo da habilidade nao pode ser nulo.");
        }
        if (!alvo.estaVivo()) {
            throw new RPGException("O alvo ja foi derrotado.");
        }
        alvo.receberDano(dano);
    }
}
