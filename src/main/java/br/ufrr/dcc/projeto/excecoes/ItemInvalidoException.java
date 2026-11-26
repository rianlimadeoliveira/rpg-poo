package br.ufrr.dcc.projeto.excecoes;

/** Lancada quando um item e invalido ou nao pode ser usado. */
public class ItemInvalidoException extends RPGException {
    private static final long serialVersionUID = 1L;

    private final String nomeItem;

    public ItemInvalidoException(String msg, String nomeItem) {
        super(msg);
        this.nomeItem = nomeItem;
    }

    public String getNomeItem() {
        return nomeItem;
    }
}
