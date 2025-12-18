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
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margem interna

        JTextField emailField = new JTextField();
        JPasswordField novaSenhaField = new JPasswordField();
        JButton recuperarBtn = new JButton("Recuperar Senha");

        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        novaSenhaField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        panel.add(new JLabel("Email cadastrado:"));
        panel.add(emailField);

        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Nova senha:"));
        panel.add(novaSenhaField);

        panel.add(Box.createVerticalStrut(20));
        recuperarBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(recuperarBtn);

        add(panel);

        recuperarBtn.addActionListener(e -> {
            String email = emailField.getText();
            String novaSenha = new String(novaSenhaField.getPassword());

            if (email.isEmpty() || novaSenha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return;
            }

            // Busca o usuário no repositório (que carregou do arquivo)
            Usuario usuario = RepositorioUsuarios.buscarPorEmail(email);

            if (usuario == null) {
                JOptionPane.showMessageDialog(this, "Email não encontrado!");
                return;
            }

            // 1. Altera a senha no objeto em memória
            usuario.setSenha(novaSenha);

            // 2. Salva a alteração no arquivo TXT
            RepositorioUsuarios.atualizarArquivo();

            JOptionPane.showMessageDialog(this, "Senha atualizada com sucesso!");
            dispose();
        });

        setVisible(true);
    }
}