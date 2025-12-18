import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

public class Exec {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gym App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // 1. Criamos o painel com a lógica de desenho corrigida
            JPanel mainPanel = new JPanel() {
                // Carregando a imagem de forma mais segura
                private Image fundo;

                {
                    String caminho = "Gemini_Generated_Image_g8ewhpg8ewhpg8ew.png";
                    File file = new File(caminho);
                    if (!file.exists()) {
                        System.err.println("ERRO: O arquivo " + caminho + " não foi encontrado na pasta: " + System.getProperty("user.dir"));
                    }
                    fundo = new ImageIcon(caminho).getImage();
                }

                @Override
                protected void paintComponent(Graphics g) {
                    // Importante: desenhar a imagem ANTES do super para que componentes fiquem por cima
                    if (fundo != null) {
                        g.drawImage(fundo, 0, 0, getWidth(), getHeight(), this);
                    }
                    // Não chamamos super.paintComponent(g) se setarmos setOpaque(false) e quisermos apenas a imagem
                }
            };

            // 2. Configurações cruciais de transparência
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setOpaque(false);

            // Título
            JLabel label = new JLabel("Bem vindo ao seu sistema de Exercícios", SwingConstants.CENTER);
            label.setFont(new Font("SansSerif", Font.BOLD, 40));
            label.setForeground(Color.WHITE);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Botões
            JButton login = new JButton("Faça login");
            JButton cad = new JButton("Cadastre-se");
            Dimension btnSize = new Dimension(200, 50);
            login.setMaximumSize(btnSize);
            cad.setMaximumSize(btnSize);

            // Link Esqueci Senha
            JLabel esq = new JLabel("Esqueci a senha");
            esq.setForeground(new Color(200, 220, 255));
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

            login.setAlignmentX(Component.CENTER_ALIGNMENT);
            cad.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Montagem do Layout
            mainPanel.add(Box.createVerticalGlue());
            mainPanel.add(label);
            mainPanel.add(Box.createVerticalStrut(50));
            mainPanel.add(login);
            mainPanel.add(Box.createVerticalStrut(20));
            mainPanel.add(cad);
            mainPanel.add(Box.createVerticalStrut(20));
            mainPanel.add(esq);
            mainPanel.add(Box.createVerticalGlue());

            // Define o painel como o ContentPane do Frame
            frame.setContentPane(mainPanel);

            frame.setSize(1920, 1080);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}