package br.ufrr.dcc.projeto.excecoes;

/** Lancada quando nao ha espaco para guardar mais elementos. */
public class SemEspacoException extends RPGException {
    private static final long serialVersionUID = 1L;

    private final int capacidade;

    public SemEspacoException(String msg, int capacidade) {
        super(msg);
        this.capacidade = capacidade;
    }

    public int getCapacidade() {
        return capacidade;
    }
}
