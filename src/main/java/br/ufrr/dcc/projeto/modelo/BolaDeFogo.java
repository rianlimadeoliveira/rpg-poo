package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.ItemInvalidoException;
import br.ufrr.dcc.projeto.excecoes.RPGException;
import br.ufrr.dcc.projeto.excecoes.VidaInvalidaException;

/** Habilidade ofensiva que causa dano adicional ao alvo. */
public class BolaDeFogo implements Habilidade {
    private final int dano;
    public BolaDeFogo(int dano) { if (dano <= 0) throw new IllegalArgumentException("O dano deve ser positivo."); this.dano = dano; }
    public BolaDeFogo() { this(25); }
    @Override public String getNome() { return "Bola de Fogo"; }
    public int getDano() { return dano; }
    @Override public void executar(Heroi usuario, Personagem alvo) throws RPGException {
        if (usuario == null || alvo == null) throw new ItemInvalidoException("Usuario e alvo sao obrigatorios.");
        if (!usuario.estaVivo() || !alvo.estaVivo()) throw new ItemInvalidoException("Usuario e alvo precisam estar vivos.");
        try { alvo.receberDano(dano); } catch (VidaInvalidaException e) { throw e; }
    }
}
