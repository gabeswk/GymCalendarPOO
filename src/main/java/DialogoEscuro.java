import javax.swing.*;
import java.awt.*;

public class DialogoEscuro {

    //mensagens simples
    public static void mostrarMensagem(Component parent, String mensagem) {
        mostrarMensagem(parent, mensagem, "Mensagem");
    }

    public static void mostrarMensagem(Component parent, String mensagem, String titulo) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), titulo, true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(450, 180);
        dialog.setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(TemaEscuro.FUNDO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel(mensagem);
        label.setForeground(TemaEscuro.TEXTO);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnOk = new JButton("OK");
        TemaEscuro.aplicarBotao(btnOk);
        btnOk.setMaximumSize(new Dimension(120, 35));
        btnOk.setPreferredSize(new Dimension(120, 35));
        btnOk.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnOk.addActionListener(e -> dialog.dispose());

        panel.add(label);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnOk);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    //mensagens de erro
    public static void mostrarErro(Component parent, String mensagem) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "Erro", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(450, 180);
        dialog.setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(TemaEscuro.FUNDO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("❌ " + mensagem);
        label.setForeground(new Color(255, 100, 100)); // Vermelho claro
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnOk = new JButton("OK");
        TemaEscuro.aplicarBotao(btnOk);
        btnOk.setMaximumSize(new Dimension(120, 35));
        btnOk.setPreferredSize(new Dimension(120, 35));
        btnOk.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnOk.addActionListener(e -> dialog.dispose());

        panel.add(label);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnOk);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    //pedir input do usuário
    public static String mostrarInput(Component parent, String mensagem) {
        return mostrarInput(parent, mensagem, "");
    }

    public static String mostrarInput(Component parent, String mensagem, String valorInicial) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "Entrada", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(450, 200);
        dialog.setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(TemaEscuro.FUNDO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel(mensagem);
        label.setForeground(TemaEscuro.TEXTO);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField campo = new JTextField(valorInicial);
        TemaEscuro.aplicarInput(campo);
        campo.setMaximumSize(new Dimension(350, 45));

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        botoesPanel.setBackground(TemaEscuro.FUNDO);

        JButton btnOk = new JButton("OK");
        JButton btnCancelar = new JButton("Cancelar");
        TemaEscuro.aplicarBotao(btnOk);
        TemaEscuro.aplicarBotao(btnCancelar);

        btnOk.setMaximumSize(new Dimension(100, 35));
        btnOk.setPreferredSize(new Dimension(100, 35));
        btnCancelar.setMaximumSize(new Dimension(100, 35));
        btnCancelar.setPreferredSize(new Dimension(100, 35));

        final String[] resultado = {null};

        btnOk.addActionListener(e -> {
            resultado[0] = campo.getText();
            dialog.dispose();
        });

        btnCancelar.addActionListener(e -> dialog.dispose());

        campo.addActionListener(e -> {
            resultado[0] = campo.getText();
            dialog.dispose();
        });

        botoesPanel.add(btnOk);
        botoesPanel.add(btnCancelar);

        panel.add(label);
        panel.add(Box.createVerticalStrut(15));
        panel.add(campo);
        panel.add(Box.createVerticalStrut(15));
        panel.add(botoesPanel);

        dialog.add(panel);
        dialog.setVisible(true);

        return resultado[0];
    }

    //confirmação (Sim ou Não)
    public static boolean mostrarConfirmacao(Component parent, String mensagem) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "Confirmação", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(450, 180);
        dialog.setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(TemaEscuro.FUNDO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel(mensagem);
        label.setForeground(TemaEscuro.TEXTO);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        botoesPanel.setBackground(TemaEscuro.FUNDO);

        JButton btnSim = new JButton("Sim");
        JButton btnNao = new JButton("Não");
        TemaEscuro.aplicarBotao(btnSim);
        TemaEscuro.aplicarBotao(btnNao);

        btnSim.setMaximumSize(new Dimension(100, 35));
        btnSim.setPreferredSize(new Dimension(100, 35));
        btnNao.setMaximumSize(new Dimension(100, 35));
        btnNao.setPreferredSize(new Dimension(100, 35));

        final boolean[] resultado = {false};

        btnSim.addActionListener(e -> {
            resultado[0] = true;
            dialog.dispose();
        });

        btnNao.addActionListener(e -> dialog.dispose());

        botoesPanel.add(btnSim);
        botoesPanel.add(btnNao);

        panel.add(label);
        panel.add(Box.createVerticalStrut(20));
        panel.add(botoesPanel);

        dialog.add(panel);
        dialog.setVisible(true);

        return resultado[0];
    }
}