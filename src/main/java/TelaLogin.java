import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {

    private JFrame execFrame;


    public TelaLogin(JFrame execFrame) {

        //Icone que fica no canto
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
        setIconImage(icon);

        this.execFrame = execFrame;
        setTitle("Login");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(TemaEscuro.FUNDO);

        JTextField emailField = new JTextField();
        JPasswordField senhaField = new JPasswordField();
        JButton loginBtn = new JButton("Entrar");

        // Criar as labels
        JLabel emailLabel = new JLabel("Email:");
        JLabel senhaLabel = new JLabel("Senha:");

        // Aplicar estilos - Adicione estas linhas
        TemaEscuro.aplicarLabel(emailLabel);
        TemaEscuro.aplicarLabel(senhaLabel);
        TemaEscuro.aplicarInput(emailField);
        TemaEscuro.aplicarInput(senhaField);
        TemaEscuro.aplicarBotao(loginBtn);

        // Adicionar componentes na ordem correta
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(senhaLabel);
        panel.add(senhaField);
        panel.add(Box.createVerticalStrut(12));
        panel.add(loginBtn);

        add(panel);

        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String senha = new String(senhaField.getPassword());

            Usuario usuario = RepositorioUsuarios.buscarPorEmail(email);

            if (usuario == null) {
                DialogoEscuro.mostrarErro(this, "Usuário não encontrado!");
                return;
            }
            if (!usuario.getSenha().equals(senha)) {
                DialogoEscuro.mostrarMensagem(this, "Senha incorreta!");
                return;
            }
            DialogoEscuro.mostrarMensagem(this, "Login realizado!");


            // Fecha somente ao logar
            dispose();           // Fecha a TelaLogin
            execFrame.dispose(); // Fecha o Exec

            new TelaHome(usuario.getNome());
        });

        setVisible(true);
    }
}
