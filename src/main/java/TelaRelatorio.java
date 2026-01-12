import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

public class TelaRelatorio extends JDialog {

    public TelaRelatorio(JFrame parent, String usuarioEmail) {
        super(parent, "Relatório", true);
        setSize(500, 600);
        setLocationRelativeTo(parent);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        // --- LÓGICA DO RELATÓRIO ---
        StringBuilder sb = new StringBuilder();
        Map<LocalDate, TreinoDoDia> todos = RepositorioTreinos.listar(usuarioEmail);

        if (todos == null || todos.isEmpty()) {
            sb.append("Sem dados.");
        } else {
            // Mapas para contagem: Nome do Treino -> Quantidade
            Map<String, Integer> totalAgendado = new HashMap<>();
            Map<String, Integer> totalConcluido = new HashMap<>();

            // Itera sobre todos os treinos
            for (TreinoDoDia t : todos.values()) {
                String nome = t.getDescricao();

                // Soma no total agendado
                totalAgendado.put(nome, totalAgendado.getOrDefault(nome, 0) + 1);

                // Se estiver marcado como concluído, soma no total concluído
                if (t.isConcluido()) {
                    totalConcluido.put(nome, totalConcluido.getOrDefault(nome, 0) + 1);
                }
            }

            sb.append("=== POR TIPO DE TREINO ===\n\n");

            for (String nome : totalAgendado.keySet()) {
                int agendados = totalAgendado.get(nome);
                int feitos = totalConcluido.getOrDefault(nome, 0);
                int faltas = agendados - feitos;

                sb.append("Treino: ").append(nome).append("\n");
                sb.append(" - Total marcado: ").append(agendados).append("\n");
                sb.append(" - Você foi:      ").append(feitos).append("\n");
                sb.append(" - Você não foi:  ").append(faltas).append("\n");
                sb.append("----------------------------\n");
            }

            sb.append("\n=== LISTA COMPLETA ===\n");
            // Ordena por data para mostrar a lista
            Map<LocalDate, TreinoDoDia> ordenados = new TreeMap<>(todos);
            for (Map.Entry<LocalDate, TreinoDoDia> entry : ordenados.entrySet()) {
                LocalDate data = entry.getKey();
                TreinoDoDia t = entry.getValue();
                String status = t.isConcluido() ? "[OK]" : "[X]";

                sb.append(data).append(" - ").append(t.getDescricao()).append(" ").append(status).append("\n");
            }
        }
        // ---------------------------

        textArea.setText(sb.toString());
        add(new JScrollPane(textArea));
    }
}