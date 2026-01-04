import ui.Estilo;
import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class Exec {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Estilo.aplicar();
            JFrame frame = new JFrame("Gym App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
            frame.setIconImage(icon);

            JPanel mainPanel = new JPanel() {
                private Image fundo;
                {
                    // Certifique-se que o arquivo está na pasta raiz do projeto
                    fundo = new ImageIcon("Backgroud.jpg").getImage();
                }

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (fundo != null) {
                        g.drawImage(fundo, 0, 0, getWidth(), getHeight(), this);
                    }
                }
            };

            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            // =====================================================
            // 🔹 PAINEL CENTRAL (ADIÇÃO – NÃO REMOVE NADA)
            // Tudo que deve ficar no centro vai aqui
            // =====================================================
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setOpaque(false); // transparente para mostrar o fundo
            // =====================================================

            // CORES - Arisnaldo
            Color fundoEscuro = new Color(30, 30, 30);
            Color campoEscuro = new Color(45, 45, 45);
            Color botaoEscuro = new Color(60, 60, 60);
            Color botaoHover = new Color(80, 80, 80);
            Color textoBranco = Color.white;

            // Título
            //JLabel label = new JLabel("Bem vindo ao seu sistema de Exercícios", SwingConstants.CENTER);
            //label.setFont(new Font("SansSerif", Font.BOLD, 40));
            //label.setForeground(Color.WHITE);
            //label.setAlignmentX(Component.CENTER_ALIGNMENT);

            //logo
            ImageIcon logoIcon = new ImageIcon("logo.png");
            Image logoImage = logoIcon.getImage();
            Image resizedImage = logoImage.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(resizedImage));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


            // INPUT LOGIN
            JTextField inputLogin = new JTextField();
            inputLogin.setMaximumSize(new Dimension(300, 45));
            inputLogin.setBackground(campoEscuro);
            inputLogin.setForeground(textoBranco);
            inputLogin.setCaretColor(textoBranco);
            inputLogin.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            inputLogin.setFont(new Font("SansSerif", Font.PLAIN, 18));
            inputLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Botão LOGIN
            JButton loginBtn = new JButton("Faça login");
            loginBtn.setBackground(botaoEscuro);
            loginBtn.setForeground(textoBranco);
            loginBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
            loginBtn.setFocusPainted(false);
            loginBtn.setBorderPainted(false);
            loginBtn.setOpaque(true);
            loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Hover Login
            loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    loginBtn.setBackground(botaoHover);
                }
                public void mouseExited(java.awt.event.MouseEvent e) {
                    loginBtn.setBackground(botaoEscuro);
                }
            });



            // Botão CADASTRO
            JButton cadBtn = new JButton("Cadastre-se");
            cadBtn.setBackground(botaoEscuro);
            cadBtn.setForeground(textoBranco);
            cadBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
            cadBtn.setFocusPainted(false);
            cadBtn.setBorderPainted(false);
            cadBtn.setOpaque(true);
            cadBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Hover Cadastro
            cadBtn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    cadBtn.setBackground(botaoHover);
                }
                public void mouseExited(java.awt.event.MouseEvent e) {
                    cadBtn.setBackground(botaoEscuro);
                }
            });

            Dimension btnSize = new Dimension(200, 50);
            loginBtn.setMaximumSize(btnSize);
            cadBtn.setMaximumSize(btnSize);

            // --- CONEXÃO DOS BOTÕES ---
            loginBtn.addActionListener(e -> new TelaLogin(frame));
            cadBtn.addActionListener(e -> new TelaCadastro());

            // Link Esqueci Senha
            JLabel esq = new JLabel("Esqueci a senha");
            esq.setForeground(Color.WHITE);
            Font baseFont = esq.getFont();
            Map<TextAttribute, Object> map = new HashMap<>(baseFont.getAttributes());
            map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            esq.setFont(baseFont.deriveFont(map).deriveFont(18f));
            esq.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            esq.setAlignmentX(Component.CENTER_ALIGNMENT);
            esq.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    new EsqueciSenha(); // Abre a tela de recuperação
                }
            });

            // =====================================================
            // MONTAGEM CENTRAL (ADIÇÃO)
            // =====================================================
            centerPanel.add(logoLabel); // Adiciona a logo primeiro
            centerPanel.add(Box.createVerticalStrut(-100)); // Espaçamento entre logo e título (ajuste o valor se quiser mais/menos)
            //centerPanel.add(label);
            centerPanel.add(Box.createVerticalStrut(40));
            centerPanel.add(Box.createVerticalStrut(25));
            centerPanel.add(loginBtn);
            centerPanel.add(Box.createVerticalStrut(15));
            centerPanel.add(cadBtn);
            centerPanel.add(Box.createVerticalStrut(15));
            centerPanel.add(esq);
            // =====================================================

            // =====================================================
            // 🔹 MONTAGEM FINAL (mantém seu Glue funcionando)
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
}
