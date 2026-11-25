package br.ufrr.dcc.projeto.excecoes;

public class VidaInvalidaException extends RPGException {
    private final int valorInformado;
    public VidaInvalidaException(String mensagem, int valorInformado) {
        super(mensagem); this.valorInformado = valorInformado;
    }
    public int getValorInformado() { return valorInformado; }
}
