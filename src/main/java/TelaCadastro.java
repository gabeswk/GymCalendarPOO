import javax.swing.*;
import java.awt.*;

public class TelaCadastro extends JFrame {
    public TelaCadastro(){

        //Icone que fica no canto
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
        setIconImage(icon);

        setTitle("Cadastro de usuario");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(TemaEscuro.FUNDO);

        // Cria os campos
        JTextField nomeField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField senhaField = new JPasswordField();
        JButton cadastrarBtn = new JButton("Cadastrar");

        // Cria as labels
        JLabel nomeLabel = new JLabel("Nome:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel senhaLabel = new JLabel("Senha:");

        // Aplica os estilos
        TemaEscuro.aplicarLabel(nomeLabel);
        TemaEscuro.aplicarLabel(emailLabel);
        TemaEscuro.aplicarLabel(senhaLabel);
        TemaEscuro.aplicarInput(nomeField);
        TemaEscuro.aplicarInput(emailField);
        TemaEscuro.aplicarInput(senhaField);
        TemaEscuro.aplicarBotao(cadastrarBtn);

        // Adiciona os componentes na ordem
        panel.add(nomeLabel);
        panel.add(nomeField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(senhaLabel);
        panel.add(senhaField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(cadastrarBtn);

        add(panel);

        cadastrarBtn.addActionListener(e -> {
            String nome = nomeField.getText();
            String email = emailField.getText();
            String senha = new String(senhaField.getPassword());

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                DialogoEscuro.mostrarMensagem(this, "Preencha todos os campos!");
                return;
            }

            if (RepositorioUsuarios.buscarPorEmail(email) != null) {
                DialogoEscuro.mostrarMensagem(this, "Email já cadastrado!");
                return;
            }

            RepositorioUsuarios.adicionar(new Usuario(nome, email, senha));
            DialogoEscuro.mostrarMensagem(this, "Cadastrado com sucesso!");
            dispose();
        });

        setVisible(true);
    }
}