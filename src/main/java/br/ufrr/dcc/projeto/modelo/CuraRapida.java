package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.ItemInvalidoException;
import br.ufrr.dcc.projeto.excecoes.RPGException;

/** Habilidade de suporte que recupera vida do proprio heroi. */
public class CuraRapida implements Habilidade {
    private final int cura;
    public CuraRapida(int cura) { if (cura <= 0) throw new IllegalArgumentException("A cura deve ser positiva."); this.cura = cura; }
    public CuraRapida() { this(20); }
    @Override public String getNome() { return "Cura Rapida"; }
    public int getCura() { return cura; }
    @Override public void executar(Heroi usuario, Personagem alvo) throws RPGException {
        if (usuario == null) throw new ItemInvalidoException("O usuario da habilidade e obrigatorio.");
        if (!usuario.estaVivo()) throw new ItemInvalidoException("Um heroi derrotado nao pode se curar.");
        usuario.recuperarVida(cura);
    }
}
