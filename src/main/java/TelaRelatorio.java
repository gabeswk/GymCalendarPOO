import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

public class TelaRelatorio extends JDialog {

    public TelaRelatorio(JFrame parent, String usuarioEmail) {
        super(parent, "Relatório", true);
        setSize(600, 700);
        setLocationRelativeTo(parent);

        // Aplica o ícone
        setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));

        // Cor de fundo do dialog
        getContentPane().setBackground(TemaEscuro.FUNDO);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(TemaEscuro.FUNDO);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titulo = new JLabel("Relatório de Treinos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(TemaEscuro.TEXTO);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // TextArea estilizada
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(TemaEscuro.CAMPO);
        textArea.setForeground(TemaEscuro.TEXTO);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        textArea.setCaretColor(TemaEscuro.TEXTO);
        textArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        textArea.setLineWrap(false);

        // --- LÓGICA DO RELATÓRIO ---
        StringBuilder sb = new StringBuilder();
        Map<LocalDate, TreinoDoDia> todos = RepositorioTreinos.listar(usuarioEmail);

        if (todos == null || todos.isEmpty()) {
            sb.append("═══════════════════════════════════════\n");
            sb.append("         SEM DADOS DISPONÍVEIS         \n");
            sb.append("═══════════════════════════════════════\n");
        } else {
            // Mapas para contagem
            Map<String, Integer> totalAgendado = new HashMap<>();
            Map<String, Integer> totalConcluido = new HashMap<>();

            for (TreinoDoDia t : todos.values()) {
                String nome = t.getDescricao();
                totalAgendado.put(nome, totalAgendado.getOrDefault(nome, 0) + 1);
                if (t.isConcluido()) {
                    totalConcluido.put(nome, totalConcluido.getOrDefault(nome, 0) + 1);
                }
            }

            sb.append("═══════════════════════════════════════\n");
            sb.append("       POR TIPO DE TREINO              \n");
            sb.append("═══════════════════════════════════════\n\n");

            for (String nome : totalAgendado.keySet()) {
                int agendados = totalAgendado.get(nome);
                int feitos = totalConcluido.getOrDefault(nome, 0);
                int faltas = agendados - feitos;

                sb.append("Treino: ").append(nome).append("\n");
                sb.append("   - Total marcado:  ").append(agendados).append("\n");
                sb.append("   - Você foi:       ").append(feitos).append("\n");
                sb.append("   - Você não foi:   ").append(faltas).append("\n");
                sb.append("───────────────────────────────────────\n");
            }

            sb.append("\n═══════════════════════════════════════\n");
            sb.append("         LISTA COMPLETA                \n");
            sb.append("═══════════════════════════════════════\n\n");

            Map<LocalDate, TreinoDoDia> ordenados = new TreeMap<>(todos);
            for (Map.Entry<LocalDate, TreinoDoDia> entry : ordenados.entrySet()) {
                LocalDate data = entry.getKey();
                TreinoDoDia t = entry.getValue();
                String status = t.isConcluido() ? "[OK]" : "[X]";

                sb.append(data).append(" - ").append(t.getDescricao())
                        .append(" ").append(status).append("\n");
            }
        }

        textArea.setText(sb.toString());

        // ScrollPane estilizado
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(TemaEscuro.BORDA, 1));
        scrollPane.getViewport().setBackground(TemaEscuro.CAMPO);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(12, 0));

        // Botão Fechar
        JButton btnFechar = new JButton("Fechar");
        TemaEscuro.aplicarBotao(btnFechar);
        btnFechar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnFechar.addActionListener(e -> dispose());

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(TemaEscuro.FUNDO);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        btnPanel.add(btnFechar);

        // Montagem
        mainPanel.add(titulo, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}