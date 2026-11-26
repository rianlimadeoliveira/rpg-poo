package br.ufrr.dcc.projeto.app;

import br.ufrr.dcc.projeto.modelo.*;
import br.ufrr.dcc.projeto.excecoes.RPGException;
import br.ufrr.dcc.projeto.excecoes.VidaInvalidaException;
import br.ufrr.dcc.projeto.repositorio.HeroiRepository;
import br.ufrr.dcc.projeto.servico.BatalhaService;
import java.util.Scanner;

public class JanelaPrincipal {
    private static final Scanner scanner = new Scanner(System.in);
    private static final HeroiRepository repositorio = new HeroiRepository();
    private static final BatalhaService batalhaService = new BatalhaService();

    public static void main(String[] args) {
        System.out.println("=== RPG POO ===");
        int opcao;
        do {
            System.out.println("\n1 - Criar heroi");
            System.out.println("2 - Listar herois");
            System.out.println("3 - Consultar heroi");
            System.out.println("4 - Batalhar");
            System.out.println("0 - Sair");
            opcao = lerInt("Escolha: ");
            try {
                switch (opcao) {
                    case 1 -> criarHeroi();
                    case 2 -> listarHerois();
                    case 3 -> consultarHeroi();
                    case 4 -> batalhar();
                    case 0 -> System.out.println("Obrigado por jogar!");
                    default -> System.out.println("Opcao invalida.");
                }
            } catch (RPGException | VidaInvalidaException | IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static void criarHeroi() throws VidaInvalidaException {
        System.out.print("Nome do heroi: ");
        String nome = scanner.nextLine().trim();
        if (repositorio.existe(nome)) {
            System.out.println("Ja existe um heroi com esse nome.");
            return;
        }
        int tipo = lerInt("1 - Guerreiro | 2 - Mago: ");
        int vida = lerInt("Vida maxima: ");
        int ataque = lerInt("Ataque: ");
        Heroi heroi;
        if (tipo == 1) {
            int forca = lerInt("Forca: ");
            heroi = new Guerreiro(nome, vida, ataque, forca);
        } else if (tipo == 2) {
            int mana = lerInt("Mana: ");
            heroi = new Mago(nome, vida, ataque, mana);
        } else {
            System.out.println("Tipo invalido.");
            return;
        }
        repositorio.salvar(heroi);
        System.out.println("Heroi cadastrado!");
    }

    private static void listarHerois() {
        if (repositorio.quantidade() == 0) {
            System.out.println("Nenhum heroi cadastrado.");
            return;
        }
        for (Heroi h : repositorio.listarTodos()) mostrarHeroi(h);
    }

    private static void consultarHeroi() {
        Heroi h = localizarHeroi();
        if (h != null) mostrarHeroi(h);
    }

    private static void batalhar() throws RPGException, VidaInvalidaException {
        Heroi h = localizarHeroi();
        if (h == null) return;
        if (!h.estaVivo()) {
            System.out.println("Este heroi esta derrotado e precisa ser recuperado antes de lutar.");
            return;
        }
        int xp = 50;
        Inimigo inimigo = new Inimigo("Goblin", 40, 5, xp, 10);
        System.out.println("Um " + inimigo.getNome() + " apareceu!");
        batalhaService.executarBatalha(h, inimigo);
        if (h.estaVivo()) {
            System.out.println("Vitoria! XP recebido: " + xp);
        } else {
            System.out.println("O heroi foi derrotado.");
        }
        mostrarHeroi(h);
    }

    private static Heroi localizarHeroi() {
        System.out.print("Nome do heroi: ");
        String nome = scanner.nextLine().trim();
        Heroi h = repositorio.buscar(nome);
        if (h == null) System.out.println("Heroi nao encontrado.");
        return h;
    }

    private static void mostrarHeroi(Heroi h) {
        System.out.println("\n--- " + h.getNome() + " ---");
        System.out.println("Nivel: " + h.getNivel() + " | XP: " + h.getXp());
        System.out.println("Vida: " + h.getVida() + "/" + h.getVidaMaxima());
        System.out.println("Ataque base: " + h.getAtaque());
        System.out.println("Dano calculado: " + h.calcularDano());
        if (h instanceof Guerreiro g) System.out.println("Forca: " + g.getForca());
        if (h instanceof Mago m) System.out.println("Mana: " + m.getMana());
    }

    private static int lerInt(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();
            try {
                return Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero inteiro.");
            }
        }
    }
}

