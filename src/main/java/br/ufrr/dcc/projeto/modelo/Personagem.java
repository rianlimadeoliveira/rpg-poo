package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.VidaInvalidaException;

/** Classe abstrata base de todo ser do jogo (herois e inimigos). */
public abstract class Personagem {
    protected String nome;
    protected int vidaMaxima;
    protected volatile int vida;
    protected int ataque;

    public Personagem(String nome, int vidaMaxima, int ataque) throws VidaInvalidaException {
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("O nome nao pode ser vazio.");
        if (vidaMaxima <= 0) throw new VidaInvalidaException("A vida maxima deve ser maior que zero.", vidaMaxima);
        if (ataque < 0) throw new IllegalArgumentException("O ataque nao pode ser negativo.");
        this.nome = nome.trim(); this.vidaMaxima = vidaMaxima; this.vida = vidaMaxima; this.ataque = ataque;
    }
    public void atacar(Personagem alvo) throws VidaInvalidaException {
        if (alvo == null) throw new IllegalArgumentException("O alvo do ataque nao pode ser nulo.");
        if (estaVivo()) alvo.receberDano(calcularDano());
    }
    public synchronized void receberDano(int valor) throws VidaInvalidaException {
        if (valor < 0) throw new VidaInvalidaException("O dano nao pode ser negativo.", valor);
        vida = Math.max(0, vida - valor);
    }
    public boolean estaVivo() { return vida > 0; }
    /** Recupera vida sem ultrapassar o limite maximo. */
    public synchronized void recuperarVida(int quantidade) throws VidaInvalidaException {
        if (quantidade < 0) throw new VidaInvalidaException("A recuperacao nao pode ser negativa.", quantidade);
        vida = Math.min(vidaMaxima, vida + quantidade);
    }
    public abstract int calcularDano();
    public String getNome() { return nome; }
    public int getVida() { return vida; }
    public int getVidaMaxima() { return vidaMaxima; }
    public int getAtaque() { return ataque; }
}
