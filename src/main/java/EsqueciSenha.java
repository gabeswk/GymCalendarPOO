import javax.swing.*;
import java.awt.*;

public class EsqueciSenha extends JFrame {

    public EsqueciSenha() {

        setTitle("Recuperar Senha");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField emailField = new JTextField();
        JPasswordField novaSenhaField = new JPasswordField();
        JButton recuperarBtn = new JButton("Recuperar Senha");

        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        novaSenhaField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        panel.add(new JLabel("Email cadastrado:"));
        panel.add(emailField);

        panel.add(new JLabel("Nova senha:"));
        panel.add(novaSenhaField);

        panel.add(Box.createVerticalStrut(12));
        panel.add(recuperarBtn);

        add(panel);

        recuperarBtn.addActionListener(e -> {
            String email = emailField.getText();
            String novaSenha = new String(novaSenhaField.getPassword());

            if (email.isEmpty() || novaSenha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return;
            }

            Usuario usuario = RepositorioUsuarios.buscarPorEmail(email);

            if (usuario == null) {
                JOptionPane.showMessageDialog(this, "Email não encontrado!");
                return;
            }

            // ALTERA A SENHA
            usuario.setSenha(novaSenha);

            JOptionPane.showMessageDialog(this, "Senha atualizada com sucesso!");
            dispose();
        });

        setVisible(true);
    }
}
