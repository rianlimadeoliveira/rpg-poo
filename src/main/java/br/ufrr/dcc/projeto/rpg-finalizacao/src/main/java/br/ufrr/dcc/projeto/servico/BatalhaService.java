package br.ufrr.dcc.projeto.servico;

import br.ufrr.dcc.projeto.modelo.Batalha;
import br.ufrr.dcc.projeto.modelo.Heroi;
import br.ufrr.dcc.projeto.modelo.Inimigo;
import br.ufrr.dcc.projeto.excecoes.RPGException;

/** Fachada para iniciar e conduzir batalhas sem concentrar regras na interface. */
public class BatalhaService {
    public Batalha iniciarBatalha(Heroi heroi, Inimigo inimigo) throws RPGException {
        Batalha batalha = new Batalha(heroi, inimigo); batalha.iniciar(); return batalha;
    }
    public void executarTurno(Batalha batalha) throws RPGException {
        if (batalha == null) throw new RPGException("A batalha nao pode ser nula.");
        batalha.executarTurno();
    }
    /** Executa ate um dos participantes ser derrotado e entrega XP ao heroi vencedor. */
    public Heroi executarBatalha(Heroi heroi, Inimigo inimigo) throws RPGException {
        Batalha batalha = iniciarBatalha(heroi, inimigo);
        while (!batalha.isFinalizada()) batalha.executarTurno();
        if (batalha.heroiVenceu()) heroi.ganharXp(inimigo.getXpRecompensa());
        return heroi;
    }
}
