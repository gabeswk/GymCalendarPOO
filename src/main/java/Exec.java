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

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            JLabel label = new JLabel("Bem vindo ao seu sistema de Exercícios", SwingConstants.CENTER);
            label.setFont(new Font("SansSerif", Font.PLAIN, 24));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton login = new JButton("Faça login");
            JButton cad = new JButton("Cadastre-se");

            // ----- Link "Esqueci a senha" -----
            JLabel esq = new JLabel("Esqueci a senha");
            esq.setForeground(new Color(0, 102, 204));
//AAAAAAAAA
            Font baseFont = esq.getFont();
            Map<TextAttribute, Object> map = new HashMap<>(baseFont.getAttributes());
            map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            esq.setFont(baseFont.deriveFont(map));

            esq.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            esq.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Evento: abrir tela de recuperar senha (NÃO fecha o Exec)
            esq.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    new EsqueciSenha();
                }
            });

            login.setAlignmentX(Component.CENTER_ALIGNMENT);
            cad.setAlignmentX(Component.CENTER_ALIGNMENT);

            mainPanel.add(Box.createVerticalStrut(50));
            mainPanel.add(label);
            mainPanel.add(Box.createVerticalStrut(30));
            mainPanel.add(login);
            mainPanel.add(cad);
            mainPanel.add(esq);

            // ----- ABRIR TELAS SEM FECHAR O EXEC -----
            login.addActionListener(e -> new TelaLogin(frame));
            cad.addActionListener(e -> new TelaCadastro());

            frame.setContentPane(mainPanel);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
