package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.RPGException;

/** Habilidade de suporte que recupera vida do alvo (normalmente o proprio heroi). */
public class CuraRapida implements Habilidade {
    private static final int CUSTO = 5;

    private final int cura;

    public CuraRapida(int cura) {
        if (cura <= 0) {
            throw new IllegalArgumentException("A cura deve ser positiva.");
        }
        this.cura = cura;
    }

    public CuraRapida() {
        this(20);
    }

    @Override
    public String getNome() {
        return "Cura Rapida";
    }

    @Override
    public int getCusto() {
        return CUSTO;
    }

    public int getCura() {
        return cura;
    }

    @Override
    public void usar(Personagem alvo) throws RPGException {
        if (alvo == null) {
            throw new IllegalArgumentException("O alvo da habilidade nao pode ser nulo.");
        }
        if (!alvo.estaVivo()) {
            throw new RPGException("Um personagem derrotado nao pode ser curado.");
        }
        alvo.recuperarVida(cura);
    }
}
