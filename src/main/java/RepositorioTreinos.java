import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class RepositorioTreinos {

    private static Map<String, Map<LocalDate, TreinoDoDia>> treinosPorUsuario = new HashMap<>();

    private static Map<LocalDate, TreinoDoDia> getMapaUsuario(String email) {
        return treinosPorUsuario.computeIfAbsent(email, k -> new HashMap<>());
    }

    public static TreinoDoDia getTreino(String email, LocalDate data) {
        return getMapaUsuario(email).get(data);
    }

    public static void salvarTreino(String email, LocalDate data, TreinoDoDia treino) {
        getMapaUsuario(email).put(data, treino);
    }

    public static boolean existeTreino(String email, LocalDate data) {
        return getMapaUsuario(email).containsKey(data);
    }

}
