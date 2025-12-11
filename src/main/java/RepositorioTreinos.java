import java.time.LocalDate;
import java.util.HashMap;

public class RepositorioTreinos {
    private static HashMap<LocalDate, TreinoDoDia> treinos = new HashMap<>();

    public static TreinoDoDia getTreino(LocalDate dia) {
        return treinos.get(dia);
    }

    public static void salvarTreino(LocalDate dia, TreinoDoDia treino) {
        treinos.put(dia, treino);
    }

    public static void removerTreino(LocalDate dia) {
        treinos.remove(dia);
    }

    public static boolean existeTreino(LocalDate dia) {
        return treinos.containsKey(dia);
    }
}
