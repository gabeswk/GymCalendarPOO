import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class RepositorioTreinos {

    // Estrutura: Email -> (Data -> Treino)
    private static Map<String, Map<LocalDate, TreinoDoDia>> treinosPorUsuario = new HashMap<>();

    // Método auxiliar privado
    private static Map<LocalDate, TreinoDoDia> getMapaUsuario(String email) {
        return treinosPorUsuario.computeIfAbsent(email, k -> new HashMap<>());
    }

    // --- Métodos de Leitura ---

    public static TreinoDoDia getTreino(String email, LocalDate data) {
        return getMapaUsuario(email).get(data);
    }

    public static boolean existeTreino(String email, LocalDate data) {
        return getMapaUsuario(email).containsKey(data);
    }

    /**
     * NOVO MÉTODO: Necessário para a TelaRelatorio.
     * Retorna todos os treinos do usuário para gerar as estatísticas.
     */
    public static Map<LocalDate, TreinoDoDia> listar(String email) {
        // Retorna o mapa de treinos ou um mapa vazio se o usuário não tiver nada
        // (Isso evita erros de NullPointerException na tela de relatório)
        return treinosPorUsuario.getOrDefault(email, new HashMap<>());
    }

    // --- Métodos de Escrita ---

    public static void salvarTreino(String email, LocalDate data, TreinoDoDia treino) {
        getMapaUsuario(email).put(data, treino);
    }

    public static void removerTreino(String email, LocalDate data) {
        getMapaUsuario(email).remove(data);
    }

    public static void removerTodosTreinosIguais(String email, String descricao) {
        Map<LocalDate, TreinoDoDia> mapa = treinosPorUsuario.get(email);

        if (mapa == null) return;

        // Remove todos os treinos que tenham a descrição igual
        mapa.entrySet().removeIf(entry ->
                entry.getValue().getDescricao().equalsIgnoreCase(descricao)
        );
    }
}