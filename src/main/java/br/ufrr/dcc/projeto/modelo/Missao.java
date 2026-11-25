package br.ufrr.dcc.projeto.modelo;

public class Missao {

    private String titulo;
    private int xpRecompensa;
    private boolean concluida;

    public Missao(String titulo, int xpRecompensa) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O titulo da missao nao pode ser vazio.");
        }
        this.titulo = titulo.trim();
        this.xpRecompensa = Math.max(0, xpRecompensa);
        this.concluida = false;
    }

    public void concluir(Heroi h) {
        if (h == null) {
            throw new IllegalArgumentException("O heroi nao pode ser nulo.");
        }
        if (concluida) {
            return; // so recompensa uma vez
        }
        concluida = true;
        h.ganharXp(xpRecompensa);
    }

    public String getTitulo() {
        return titulo;
    }

    public int getXpRecompensa() {
        return xpRecompensa;
    }

    public boolean isConcluida() {
        return concluida;
    }
}
