import javax.swing.*;
import java.awt.*;

public class TelaCadastro extends JFrame {
    public TelaCadastro(){
        setTitle("Cadastro de usuario");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField nomeField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField senhaField = new JPasswordField();
        JButton cadastrarBtn = new JButton("Cadastrar");

        nomeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        senhaField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Senha:"));
        panel.add(senhaField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(cadastrarBtn);

        add(panel);

        cadastrarBtn.addActionListener(e -> {
            String nome = nomeField.getText();
            String email = emailField.getText();
            String senha = new String(senhaField.getPassword());

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return;
            }

            if (RepositorioUsuarios.buscarPorEmail(email) != null) {
                JOptionPane.showMessageDialog(this, "Email já cadastrado!");
                return;
            }

            RepositorioUsuarios.adicionar(new Usuario(nome, email, senha));
            JOptionPane.showMessageDialog(this, "Cadastrado com sucesso!");
            dispose();
        });

        setVisible(true);
    }
}
