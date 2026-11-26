package br.ufrr.dcc.projeto.excecoes;

/** Lancada quando um valor de vida (ou de dano/cura sobre a vida) e invalido. */
public class VidaInvalidaException extends RPGException {
    private static final long serialVersionUID = 1L;

    private final int vidaAtual;

    public VidaInvalidaException(String msg, int vidaAtual) {
        super(msg);
        this.vidaAtual = vidaAtual;
    }

    public int getVidaAtual() {
        return vidaAtual;
    }
}
