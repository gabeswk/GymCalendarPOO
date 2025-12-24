import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class Exec {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gym App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel() {
                private Image fundo;
                {
                    // Certifique-se que o arquivo está na pasta raiz do projeto
                    fundo = new ImageIcon("Gemini_Generated_Image_g8ewhpg8ewhpg8ew.png").getImage();
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

            // Título
            JLabel label = new JLabel("Bem vindo ao seu sistema de Exercícios", SwingConstants.CENTER);
            label.setFont(new Font("SansSerif", Font.BOLD, 40));
            label.setForeground(Color.WHITE);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Botões
            JButton loginBtn = new JButton("Faça login");
            JButton cadBtn = new JButton("Cadastre-se");
            Dimension btnSize = new Dimension(200, 50);
            loginBtn.setMaximumSize(btnSize);
            cadBtn.setMaximumSize(btnSize);
            loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            cadBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

            // --- CONEXÃO DOS BOTÕES ---

            // Login: Passa o 'frame' atual para que a TelaLogin possa fechá-lo depois
            loginBtn.addActionListener(e -> {
                new TelaLogin(frame);
            });

            // Cadastro: Abre a tela de cadastro
            cadBtn.addActionListener(e -> {
                new TelaCadastro();
            });

            // --- FIM DA CONEXÃO ---

            // Link Esqueci Senha
            JLabel esq = new JLabel("Esqueci a senha");
            esq.setForeground(Color.WHITE);
            Font baseFont = esq.getFont();
            Map<TextAttribute, Object> map = new HashMap<>(baseFont.getAttributes());
            map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            esq.setFont(baseFont.deriveFont(map).deriveFont(18f));
            esq.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            esq.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Montagem
            mainPanel.add(Box.createVerticalGlue());
            mainPanel.add(label);
            mainPanel.add(Box.createVerticalStrut(50));
            mainPanel.add(loginBtn);
            mainPanel.add(Box.createVerticalStrut(20));
            mainPanel.add(cadBtn);
            mainPanel.add(Box.createVerticalStrut(20));
            mainPanel.add(esq);
            mainPanel.add(Box.createVerticalGlue());

            frame.setContentPane(mainPanel);
            frame.setSize(1280, 720);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}