import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {

    private JFrame execFrame;

    public TelaLogin(JFrame execFrame) {
        this.execFrame = execFrame;

        setTitle("Login");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField emailField = new JTextField();
        JPasswordField senhaField = new JPasswordField();
        JButton loginBtn = new JButton("Entrar");

        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        senhaField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.add(new JLabel("Senha:"));
        panel.add(senhaField);

        panel.add(Box.createVerticalStrut(12));
        panel.add(loginBtn);

        add(panel);

        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String senha = new String(senhaField.getPassword());

            Usuario usuario = RepositorioUsuarios.buscarPorEmail(email);

            if (usuario == null) {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado!");
                return;
            }
            if (!usuario.getSenha().equals(senha)) {
                JOptionPane.showMessageDialog(this, "Senha incorreta!");
                return;
            }

            JOptionPane.showMessageDialog(this, "Login realizado!");

            // Fecha somente ao logar
            dispose();           // Fecha a TelaLogin
            execFrame.dispose(); // Fecha o Exec

            new TelaHome(usuario.getNome());
        });

        setVisible(true);
    }
}
