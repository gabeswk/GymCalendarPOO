import ui.Estilo;
import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class Exec {
    public static void abrirTelaInicial() {
        SwingUtilities.invokeLater(() -> {
            Estilo.aplicar();

            JFrame frame = new JFrame("Gym App");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
            frame.setIconImage(icon);

            // =====================================================
            // PAINEL PRINCIPAL
            // =====================================================
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBackground(TemaEscuro.FUNDO); // Usa cor do TemaEscuro
            // =====================================================

            // =====================================================
            // PAINEL CENTRAL
            // =====================================================
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setOpaque(false);
            // =====================================================

            // Logo
            ImageIcon logoIcon = new ImageIcon("logo.png");
            Image logoImage = logoIcon.getImage();
            Image resizedImage = logoImage.getScaledInstance(310, 310, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(resizedImage));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Botão LOGIN
            JButton loginBtn = new JButton("Faça login");
            configurarBotaoTelaInicial(loginBtn);

            // Botão CADASTRO
            JButton cadBtn = new JButton("Cadastre-se");
            configurarBotaoTelaInicial(cadBtn);

            // Define tamanho dos botões
            Dimension btnSize = new Dimension(200, 50);
            loginBtn.setMaximumSize(btnSize);
            cadBtn.setMaximumSize(btnSize);

            // --- CONEXÃO DOS BOTÕES ---
            loginBtn.addActionListener(e -> new TelaLogin(frame));
            cadBtn.addActionListener(e -> new TelaCadastro());

            // Link Esqueci Senha
            JLabel esq = new JLabel("Esqueci a senha");
            esq.setForeground(TemaEscuro.TEXTO); // Usa cor do TemaEscuro
            Font baseFont = esq.getFont();
            Map<TextAttribute, Object> map = new HashMap<>(baseFont.getAttributes());
            map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            esq.setFont(baseFont.deriveFont(map).deriveFont(18f));
            esq.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            esq.setAlignmentX(Component.CENTER_ALIGNMENT);
            esq.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    new EsqueciSenha();
                }
            });

            // =====================================================
            // MONTAGEM CENTRAL
            // =====================================================
            centerPanel.add(logoLabel);
            centerPanel.add(Box.createVerticalStrut(20));
            centerPanel.add(loginBtn);
            centerPanel.add(Box.createVerticalStrut(15));
            centerPanel.add(cadBtn);
            centerPanel.add(Box.createVerticalStrut(15));
            centerPanel.add(esq);
            // =====================================================

            // =====================================================
            // MONTAGEM FINAL
            // =====================================================
            mainPanel.add(Box.createVerticalGlue());
            mainPanel.add(centerPanel);
            mainPanel.add(Box.createVerticalGlue());
            // =====================================================

            frame.setContentPane(mainPanel);
            frame.setSize(1280, 720);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }

    // Método auxiliar para configurar botões da tela inicial
    private static void configurarBotaoTelaInicial(JButton botao) {
        botao.setBackground(new Color(60, 60, 60)); // Cinza escuro
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("SansSerif", Font.BOLD, 16));
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setOpaque(true);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Hover effect
        Color hoverColor = new Color(80, 80, 80);
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                botao.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                botao.setBackground(new Color(60, 60, 60));
            }
        });
    }

    public static void main(String[] args) {
        abrirTelaInicial();
    }
}