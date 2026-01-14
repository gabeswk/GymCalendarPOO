import javax.swing.*;

import java.awt.*;

public class TelaCadastro extends JFrame {
    public TelaCadastro() {
        //Ícone que fica no canto
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
        setIconImage(icon);

        setTitle("Cadastro de Usuário");
        setSize(440, 380); // Aumentei o tamanho para acomodar 3 campos
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Aplicar cor de fundo na Janela
        getContentPane().setBackground(TemaEscuro.FUNDO);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // Margens laterais
        panel.setBackground(TemaEscuro.FUNDO);

        //Criar os campos
        JTextField nomeField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField senhaField = new JPasswordField();
        JButton cadastrarBtn = new JButton("Cadastrar");

        //Criar as labels
        JLabel nomeLabel = new JLabel("Nome:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel senhaLabel = new JLabel("Senha:");

        //Aplicar estilos
        TemaEscuro.aplicarLabel(nomeLabel);
        TemaEscuro.aplicarLabel(emailLabel);
        TemaEscuro.aplicarLabel(senhaLabel);
        TemaEscuro.aplicarInput(nomeField);
        TemaEscuro.aplicarInput(emailField);
        TemaEscuro.aplicarInput(senhaField);
        TemaEscuro.aplicarBotao(cadastrarBtn);

        //Adicionar componentes com espaçamentos
        panel.add(nomeLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(nomeField);

        panel.add(Box.createVerticalStrut(20)); // Espaço entre os grupos

        panel.add(emailLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(emailField);

        panel.add(Box.createVerticalStrut(20)); // Espaço entre os grupos

        panel.add(senhaLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(senhaField);

        panel.add(Box.createVerticalStrut(30)); // Espaço antes do botão
        panel.add(cadastrarBtn);

        add(panel);

        cadastrarBtn.addActionListener(e -> {
            String nome = nomeField.getText();
            String email = emailField.getText();
            String senha = new String(senhaField.getPassword());

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                DialogoEscuro.mostrarErro(this, "Preencha todos os campos!");
                return;
            }

            if (RepositorioUsuarios.buscarPorEmail(email) != null) {
                DialogoEscuro.mostrarErro(this, "Email já cadastrado!");
                return;
            }

            RepositorioUsuarios.adicionar(new Usuario(nome, email, senha));
            DialogoEscuro.mostrarMensagem(this, "Cadastrado com sucesso!");
            dispose();
        });

        setVisible(true);
    }
}