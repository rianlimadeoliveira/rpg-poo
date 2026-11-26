package br.ufrr.dcc.projeto.excecoes;

/** Excecao base para erros esperados pelas regras do RPG. */
public class RPGException extends Exception {
    private static final long serialVersionUID = 1L;

    public RPGException(String msg) {
        super(msg);
    }
}
