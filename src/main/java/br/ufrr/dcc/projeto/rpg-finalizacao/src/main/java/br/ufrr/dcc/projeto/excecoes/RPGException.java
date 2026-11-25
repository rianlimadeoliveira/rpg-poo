package br.ufrr.dcc.projeto.excecoes;

/** Excecao base para erros esperados pelas regras do RPG. */
public class RPGException extends Exception {
    public RPGException(String mensagem) { super(mensagem); }
    public RPGException(String mensagem, Throwable causa) { super(mensagem, causa); }
}
