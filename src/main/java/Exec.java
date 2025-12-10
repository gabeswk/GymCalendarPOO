import javax.swing.*;
import java.awt.*;

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
            JButton esq = new JButton("Esqueci a senha");

            login.setAlignmentX(Component.CENTER_ALIGNMENT);
            cad.setAlignmentX(Component.CENTER_ALIGNMENT);

            mainPanel.add(Box.createVerticalStrut(50));
            mainPanel.add(label);
            mainPanel.add(Box.createVerticalStrut(30));
            mainPanel.add(login);
            mainPanel.add(cad);
            mainPanel.add(esq);


            login.addActionListener(e -> new TelaLogin());
            cad.addActionListener(e -> new TelaCadastro());

            frame.setContentPane(mainPanel);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
