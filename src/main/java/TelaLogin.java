import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {
    public TelaLogin() {
        setTitle("Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField emailField = new JTextField();
        JPasswordField senhaField = new JPasswordField();
        JButton entrarBtn = new JButton("Entrar");

        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        senhaField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Senha:"));
        panel.add(senhaField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(entrarBtn);

        add(panel);

        entrarBtn.addActionListener(e -> {
            String email = emailField.getText();
            String senha = new String(senhaField.getPassword());

            Usuario u = RepositorioUsuarios.buscarPorEmail(email);

            if (u == null || !u.getSenha().equals(senha)) {
                JOptionPane.showMessageDialog(this, "Login inválido!");
                return;
            }

            JOptionPane.showMessageDialog(this, "Bem-vindo, " + u.getNome() + "!");
            dispose();
        });

        setVisible(true);
    }
}
