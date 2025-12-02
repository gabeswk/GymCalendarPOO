// Exec.java
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.Font;

public class Exec {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Janela Hello");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JLabel label = new JLabel("hello world!", SwingConstants.CENTER);
            label.setFont(new Font("SansSerif", Font.PLAIN, 24));

            frame.getContentPane().add(label);
            frame.setSize(350, 150);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null); // centraliza na tela
            frame.setVisible(true);
        });
    }
}
