package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.VidaInvalidaException;

/**
 * Classe abstrata base de todo ser do jogo (herois e inimigos).
 * Os atributos sao protected (conforme o UML) para que as subclasses
 * e as classes do pacote modelo possam usa-los.
 */
public abstract class Personagem {

    protected String nome;
    protected int vidaMaxima;
    // volatile: a batalha roda em outra thread e a janela le a vida
    protected volatile int vida;
    protected int ataque;

    public Personagem(String nome, int vidaMaxima, int ataque) throws VidaInvalidaException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome nao pode ser vazio.");
        }
        if (vidaMaxima <= 0) {
            throw new VidaInvalidaException("A vida maxima deve ser maior que zero.", vidaMaxima);
        }
        this.nome = nome.trim();
        this.vidaMaxima = vidaMaxima;
        this.vida = vidaMaxima;
        this.ataque = ataque;
    }

    public void atacar(Personagem alvo) throws VidaInvalidaException {
        if (alvo == null) {
            throw new IllegalArgumentException("O alvo do ataque nao pode ser nulo.");
        }
        if (!estaVivo()) {
            return; // quem esta morto nao ataca
        }
        alvo.receberDano(calcularDano());
    }

    // synchronized: evita perder dano se duas threads atingirem o mesmo alvo
    public synchronized void receberDano(int valor) throws VidaInvalidaException {
        if (valor < 0) {
            throw new VidaInvalidaException("O dano nao pode ser negativo.", vida);
        }
        vida = Math.max(0, vida - valor);
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public abstract int calcularDano();

    public String getNome() {
        return nome;
    }

    public int getVida() {
        return vida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getAtaque() {
        return ataque;
    }
}
