package br.ufrr.dcc.projeto.modelo;

import br.ufrr.dcc.projeto.excecoes.VidaInvalidaException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Heroi extends Personagem {
    private volatile int nivel;
    private volatile int xp;
    private final Inventario inventario;
    private final List<Missao> missoes;
    private final List<Habilidade> habilidades;

    public Heroi(String nome, int vidaMaxima, int ataque) throws VidaInvalidaException {
        super(nome, vidaMaxima, ataque);
        this.nivel = 1; this.xp = 0; this.inventario = new Inventario(10);
        this.missoes = new ArrayList<>(); this.habilidades = new ArrayList<>();
    }
    @Override public int calcularDano() { return ataque + nivel; }
    public synchronized void ganharXp(int qtd) {
        if (qtd <= 0) return;
        xp += qtd;
        while (xp >= xpParaProximoNivel()) subirNivel();
    }
    public synchronized void subirNivel() {
        xp = Math.max(0, xp - xpParaProximoNivel()); nivel++;
        vidaMaxima += 10; vida = vidaMaxima; ataque += 2;
    }
    private int xpParaProximoNivel() { return nivel * 100; }
    public int getNivel() { return nivel; }
    public int getXp() { return xp; }
    public Inventario getInventario() { return inventario; }
    public void adicionarMissao(Missao missao) {
        if (missao == null) throw new IllegalArgumentException("A missao nao pode ser nula.");
        missoes.add(missao);
    }
    public List<Missao> getMissoes() { return Collections.unmodifiableList(missoes); }
    public void aprenderHabilidade(Habilidade habilidade) {
        if (habilidade == null) throw new IllegalArgumentException("A habilidade nao pode ser nula.");
        habilidades.add(habilidade);
    }
    public List<Habilidade> getHabilidades() { return Collections.unmodifiableList(habilidades); }
    public void aumentarAtaque(int bonus) {
        if (bonus < 0) throw new IllegalArgumentException("O bonus nao pode ser negativo.");
        ataque += bonus;
    }
}
