package br.ufrr.dcc.projeto.app;

import br.ufrr.dcc.projeto.modelo.Arqueiro;
import br.ufrr.dcc.projeto.modelo.Guerreiro;
import br.ufrr.dcc.projeto.modelo.Heroi;
import br.ufrr.dcc.projeto.modelo.Inimigo;
import br.ufrr.dcc.projeto.modelo.Mago;
import br.ufrr.dcc.projeto.servico.BatalhaService;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * Janela principal do jogo. Cadastra o heroi, inicia a batalha (que roda em
 * outra thread, dentro do BatalhaService) e acompanha o andamento na tela.
 */
public class JanelaPrincipal extends JFrame {

    private static final int INTERVALO_ATUALIZACAO_MS = 200;
    private static final int TICKS_APOS_FIM = 3; // espera a thread da batalha terminar de premiar o heroi

    private BatalhaService servico;

    private JTextField campoNome;
    private JComboBox<String> comboClasse;
    private JButton botaoCadastrar;
    private JButton botaoIniciar;
    private JLabel rotuloHeroi;
    private JLabel rotuloInimigo;
    private JLabel rotuloStatus;
    private JProgressBar barraHeroi;
    private JProgressBar barraInimigo;
    private JTextArea areaLog;
    private Timer temporizador;

    private Heroi heroi;
    private Inimigo inimigo;
    private int vidaHeroiAnterior;
    private int vidaInimigoAnterior;
    private int ticksAposFim;

    public JanelaPrincipal() {
        super("RPG - Batalha");
        this.servico = new BatalhaService();
        montarTela();
    }

    public void exibirBatalha() {
        setVisible(true);
    }

    private void montarTela() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(580, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        // Topo: cadastro do heroi
        JPanel painelCadastro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Cadastro do heroi"));
        campoNome = new JTextField(12);
        comboClasse = new JComboBox<>(new String[]{"Guerreiro", "Mago", "Arqueiro"});
        botaoCadastrar = new JButton("Cadastrar heroi");
        botaoCadastrar.addActionListener(e -> cadastrarHeroi());
        painelCadastro.add(new JLabel("Nome:"));
        painelCadastro.add(campoNome);
        painelCadastro.add(new JLabel("Classe:"));
        painelCadastro.add(comboClasse);
        painelCadastro.add(botaoCadastrar);
        add(painelCadastro, BorderLayout.NORTH);

        // Centro: status dos combatentes e log
        JPanel painelStatus = new JPanel(new GridLayout(0, 1, 4, 4));
        painelStatus.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
        rotuloHeroi = new JLabel("Nenhum heroi cadastrado");
        barraHeroi = criarBarra();
        rotuloInimigo = new JLabel("Nenhum inimigo em combate");
        barraInimigo = criarBarra();
        rotuloStatus = new JLabel("Cadastre um heroi para comecar.");
        painelStatus.add(rotuloHeroi);
        painelStatus.add(barraHeroi);
        painelStatus.add(rotuloInimigo);
        painelStatus.add(barraInimigo);
        painelStatus.add(rotuloStatus);

        areaLog = new JTextArea(8, 40);
        areaLog.setEditable(false);
        JScrollPane rolagem = new JScrollPane(areaLog);
        rolagem.setBorder(BorderFactory.createTitledBorder("Registro da batalha"));

        JPanel painelCentro = new JPanel(new BorderLayout(4, 4));
        painelCentro.add(painelStatus, BorderLayout.NORTH);
        painelCentro.add(rolagem, BorderLayout.CENTER);
        add(painelCentro, BorderLayout.CENTER);

        // Rodape: iniciar batalha
        JPanel painelBotoes = new JPanel();
        botaoIniciar = new JButton("Iniciar batalha");
        botaoIniciar.setEnabled(false);
        botaoIniciar.addActionListener(e -> iniciarBatalha());
        painelBotoes.add(botaoIniciar);
        add(painelBotoes, BorderLayout.SOUTH);

        // Swing Timer roda na thread da interface: e seguro mexer nos componentes aqui
        temporizador = new Timer(INTERVALO_ATUALIZACAO_MS, e -> atualizarBatalha());
    }

    private JProgressBar criarBarra() {
        JProgressBar barra = new JProgressBar(0, 1);
        barra.setStringPainted(true);
        return barra;
    }

    private void cadastrarHeroi() {
        try {
            String classe = (String) comboClasse.getSelectedItem();
            String nome = campoNome.getText();
            Heroi novo;
            if ("Mago".equals(classe)) {
                novo = new Mago(nome, 80, 8, 50);
            } else if ("Arqueiro".equals(classe)) {
                novo = new Arqueiro(nome, 90, 9, 7);
            } else {
                novo = new Guerreiro(nome, 120, 10, 8);
            }
            servico.cadastrar(novo);
            heroi = novo;
            inimigo = null;
            botaoIniciar.setEnabled(true);
            rotuloStatus.setText("Heroi pronto. Clique em Iniciar batalha.");
            log("Heroi cadastrado: " + novo.getNome() + " (" + classe + ")");
            atualizarTela();
        } catch (Exception ex) {
            mostrarErro(ex);
        }
    }

    private void iniciarBatalha() {
        if (heroi == null) {
            mostrarErro("Cadastre um heroi antes de iniciar a batalha.");
            return;
        }
        if (!heroi.estaVivo()) {
            mostrarErro("O heroi foi derrotado. Cadastre outro heroi.");
            return;
        }
        try {
            Inimigo novoInimigo = new Inimigo("Goblin", 100, 8, 60, 20);
            servico.iniciar(heroi, novoInimigo);
            inimigo = novoInimigo;
            vidaHeroiAnterior = heroi.getVida();
            vidaInimigoAnterior = inimigo.getVida();
            ticksAposFim = 0;
            botaoIniciar.setEnabled(false);
            botaoCadastrar.setEnabled(false);
            rotuloStatus.setText("Batalha em andamento...");
            log("Batalha iniciada: " + heroi.getNome() + " x " + inimigo.getNome());
            atualizarTela();
            temporizador.start();
        } catch (Exception ex) {
            mostrarErro(ex);
        }
    }

    private void atualizarBatalha() {
        atualizarTela();

        int vidaHeroi = heroi.getVida();
        int vidaInimigo = inimigo.getVida();
        if (vidaHeroi < vidaHeroiAnterior) {
            log(inimigo.getNome() + " causou " + (vidaHeroiAnterior - vidaHeroi)
                    + " de dano em " + heroi.getNome());
        }
        if (vidaInimigo < vidaInimigoAnterior) {
            log(heroi.getNome() + " causou " + (vidaInimigoAnterior - vidaInimigo)
                    + " de dano em " + inimigo.getNome());
        }
        vidaHeroiAnterior = vidaHeroi;
        vidaInimigoAnterior = vidaInimigo;

        if (!heroi.estaVivo() || !inimigo.estaVivo()) {
            ticksAposFim++;
            if (ticksAposFim >= TICKS_APOS_FIM) {
                finalizarBatalha();
            }
        }
    }

    private void finalizarBatalha() {
        temporizador.stop();
        if (heroi.estaVivo()) {
            rotuloStatus.setText("Vitoria! " + heroi.getNome() + " derrotou " + inimigo.getNome() + ".");
            log("Vitoria! XP ganho: " + inimigo.getXpRecompensa());
        } else {
            rotuloStatus.setText("Derrota. " + heroi.getNome() + " foi derrotado.");
            log("Derrota.");
        }
        atualizarTela();
        botaoCadastrar.setEnabled(true);
        botaoIniciar.setEnabled(heroi.estaVivo());
    }

    private void atualizarTela() {
        if (heroi != null) {
            rotuloHeroi.setText(heroi.getNome() + " (" + heroi.getClass().getSimpleName()
                    + ") - Nivel " + heroi.getNivel() + " | XP " + heroi.getXp());
            atualizarBarra(barraHeroi, heroi.getVida(), heroi.getVidaMaxima());
        }
        if (inimigo != null) {
            rotuloInimigo.setText(inimigo.getNome() + " - recompensa: "
                    + inimigo.getXpRecompensa() + " XP, " + inimigo.getOuro() + " de ouro");
            atualizarBarra(barraInimigo, inimigo.getVida(), inimigo.getVidaMaxima());
        }
    }

    private void atualizarBarra(JProgressBar barra, int vida, int vidaMaxima) {
        barra.setMaximum(Math.max(1, vidaMaxima));
        barra.setValue(vida);
        barra.setString(vida + " / " + vidaMaxima);
    }

    private void log(String mensagem) {
        areaLog.append(mensagem + "\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }

    private void mostrarErro(Exception ex) {
        String mensagem = ex.getMessage();
        mostrarErro(mensagem != null ? mensagem : ex.toString());
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JanelaPrincipal().exibirBatalha());
    }
}
