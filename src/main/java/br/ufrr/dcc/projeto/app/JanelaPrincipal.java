package br.ufrr.dcc.projeto.app;

import br.ufrr.dcc.projeto.excecoes.RPGException;
import br.ufrr.dcc.projeto.modelo.Arqueiro;
import br.ufrr.dcc.projeto.modelo.Guerreiro;
import br.ufrr.dcc.projeto.modelo.Heroi;
import br.ufrr.dcc.projeto.modelo.Inimigo;
import br.ufrr.dcc.projeto.modelo.Mago;
import br.ufrr.dcc.projeto.repositorio.HeroiRepository;
import br.ufrr.dcc.projeto.servico.BatalhaService;
import java.util.Scanner;

/** Interface de terminal do jogo. Toda regra de negocio fica no BatalhaService. */
public class JanelaPrincipal {

    private final BatalhaService servico;
    private final Scanner scanner;

    public JanelaPrincipal(BatalhaService servico) {
        this.servico = servico;
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new JanelaPrincipal(new BatalhaService()).executar();
    }

    private void executar() {
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
                    case 4 -> exibirBatalha();
                    case 0 -> System.out.println("Obrigado por jogar!");
                    default -> System.out.println("Opcao invalida.");
                }

            } catch (RPGException | IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (opcao != 0);
    }

    public void exibirBatalha() throws RPGException {
        Heroi h = localizarHeroi();

        if (h == null) {
            return;
        }

        if (!h.estaVivo()) {
            System.out.println("Este heroi esta derrotado e precisa ser recuperado antes de lutar.");
            return;
        }

        Inimigo inimigo = new Inimigo("Goblin", 40, 5, 50, 10);

        System.out.println("Um " + inimigo.getNome() + " apareceu!");

        servico.iniciar(h, inimigo);

        if (h.estaVivo()) {
            System.out.println("Vitoria! XP recebido: " + inimigo.getXpRecompensa());
        } else {
            System.out.println("O heroi foi derrotado.");
        }

        mostrarHeroi(h);
    }

    private void criarHeroi() throws RPGException {
        System.out.print("Nome do heroi: ");
        String nome = scanner.nextLine().trim();

        if (repositorio().existe(nome)) {
            System.out.println("Ja existe um heroi com esse nome.");
            return;
        }

        int tipo = lerInt("1 - Guerreiro | 2 - Mago | 3 - Arqueiro: ");
        int vida = lerInt("Vida maxima: ");
        int ataque = lerInt("Ataque: ");

        Heroi heroi;

        if (tipo == 1) {
            int forca = lerInt("Forca: ");
            heroi = new Guerreiro(nome, vida, ataque, forca);

        } else if (tipo == 2) {
            int mana = lerInt("Mana: ");
            heroi = new Mago(nome, vida, ataque, mana);

        } else if (tipo == 3) {
            int precisao = lerInt("Precisao: ");
            heroi = new Arqueiro(nome, vida, ataque, precisao);

        } else {
            System.out.println("Tipo invalido.");
            return;
        }

        servico.cadastrar(heroi);
        System.out.println("Heroi cadastrado!");
    }

    private void listarHerois() {
        if (repositorio().quantidade() == 0) {
            System.out.println("Nenhum heroi cadastrado.");
            return;
        }

        for (Heroi h : repositorio().listarTodos()) {
            mostrarHeroi(h);
        }
    }

    private void consultarHeroi() {
        Heroi h = localizarHeroi();

        if (h != null) {
            mostrarHeroi(h);
        }
    }

    private Heroi localizarHeroi() {
        System.out.print("Nome do heroi: ");

        String nome = scanner.nextLine().trim();

        Heroi h = repositorio().buscar(nome);

        if (h == null) {
            System.out.println("Heroi nao encontrado.");
        }

        return h;
    }

    private HeroiRepository repositorio() {
        return servico.getRepo();
    }

    private void mostrarHeroi(Heroi h) {
        System.out.println("\n--- " + h.getNome() + " ---");
        System.out.println("Nivel: " + h.getNivel() + " | XP: " + h.getXp());
        System.out.println("Vida: " + h.getVida() + "/" + h.getVidaMaxima());
        System.out.println("Ataque base: " + h.getAtaque());
        System.out.println("Dano calculado: " + h.calcularDano());

        if (h instanceof Guerreiro g) {
            System.out.println("Forca: " + g.getForca());
        }

        if (h instanceof Mago m) {
            System.out.println("Mana: " + m.getMana());
        }

        if (h instanceof Arqueiro a) {
            System.out.println("Precisao: " + a.getPrecisao());
        }
    }

    private int lerInt(String mensagem) {
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
