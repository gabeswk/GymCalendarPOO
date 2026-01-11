import javax.swing.*;

import java.awt.*;

public class EsqueciSenha extends JFrame {

    public EsqueciSenha() {
        // Configuração Inicial
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
        setIconImage(icon);

        setTitle("Recuperar Senha");
        setSize(500, 320); // Aumentei a largura para acomodar melhor
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Aplicar cor de fundo na Janela
        getContentPane().setBackground(TemaEscuro.FUNDO);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50)); // Margens laterais maiores
        panel.setBackground(TemaEscuro.FUNDO);

        // Componentes
        JLabel lblEmail = new JLabel("Email cadastrado:");
        JTextField emailField = new JTextField();

        JLabel lblSenha = new JLabel("Nova senha:");
        JPasswordField novaSenhaField = new JPasswordField();

        JButton recuperarBtn = new JButton("Recuperar Senha");

        // Estilizar Labels
        TemaEscuro.aplicarLabel(lblEmail);
        TemaEscuro.aplicarLabel(lblSenha);

        // Estilizar Inputs
        TemaEscuro.aplicarInput(emailField);
        TemaEscuro.aplicarInput(novaSenhaField);

        // Estilizar Botão
        TemaEscuro.aplicarBotao(recuperarBtn);

        // Adicionando ao painel com espaçamentos
        panel.add(lblEmail);
        panel.add(Box.createVerticalStrut(8));
        panel.add(emailField);

        panel.add(Box.createVerticalStrut(20)); // Espaço entre os grupos

        panel.add(lblSenha);
        panel.add(Box.createVerticalStrut(8));
        panel.add(novaSenhaField);

        panel.add(Box.createVerticalStrut(30)); // Espaço antes do botão
        panel.add(recuperarBtn);

        add(panel);

        // Lógica do botão
        recuperarBtn.addActionListener(e -> {
            String email = emailField.getText();
            String novaSenha = new String(novaSenhaField.getPassword());

            if (email.isEmpty() || novaSenha.isEmpty()) {
                DialogoEscuro.mostrarErro(this, "Preencha todos os campos!");
                return;
            }

            Usuario usuario = RepositorioUsuarios.buscarPorEmail(email);

            if (usuario == null) {
                DialogoEscuro.mostrarErro(this, "Email não encontrado!");
                return;
            }

            usuario.setSenha(novaSenha);
            RepositorioUsuarios.atualizarArquivo();

            DialogoEscuro.mostrarMensagem(this, "Senha atualizada com sucesso!");
            dispose();
        });

        setVisible(true);
    }
}