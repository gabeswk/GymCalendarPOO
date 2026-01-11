import javax.swing.*;

import java.awt.*;

public class TelaLogin extends JFrame {

    private JFrame execFrame;

    public TelaLogin(JFrame execFrame) {
        // Ícone que fica no canto
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
        setIconImage(icon);

        this.execFrame = execFrame;
        setTitle("Login");
        setSize(440, 300); // Aumentei o tamanho
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Aplicar cor de fundo na Janela
        getContentPane().setBackground(TemaEscuro.FUNDO);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // Margens laterais
        panel.setBackground(TemaEscuro.FUNDO);

        JTextField emailField = new JTextField();
        JPasswordField senhaField = new JPasswordField();
        JButton loginBtn = new JButton("Entrar");

        // Criar as labels
        JLabel emailLabel = new JLabel("Email:");
        JLabel senhaLabel = new JLabel("Senha:");

        // Aplicar estilos
        TemaEscuro.aplicarLabel(emailLabel);
        TemaEscuro.aplicarLabel(senhaLabel);
        TemaEscuro.aplicarInput(emailField);
        TemaEscuro.aplicarInput(senhaField);
        TemaEscuro.aplicarBotao(loginBtn);

        // Adicionar componentes com espaçamentos
        panel.add(emailLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(emailField);

        panel.add(Box.createVerticalStrut(20)); // Espaço entre os grupos

        panel.add(senhaLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(senhaField);

        panel.add(Box.createVerticalStrut(30)); // Espaço antes do botão
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
                DialogoEscuro.mostrarErro(this, "Senha incorreta!");
                return;
            }
            DialogoEscuro.mostrarMensagem(this, "Login realizado!");

            // Fecha somente ao logar
            dispose(); // Fecha a TelaLogin
            execFrame.dispose(); // Fecha o Exec

            new TelaHome(usuario.getEmail()); // Usa email ao invés de nome
        });

        setVisible(true);
    }
}