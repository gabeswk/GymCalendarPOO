import java.util.ArrayList;

public class RepositorioUsuarios {
    private static ArrayList<Usuario> usuarios = new ArrayList<>();

    public static void adicionar(Usuario u){
        usuarios.add(u);
    }

    public static Usuario buscarPorEmail(String email){
        for(Usuario u : usuarios){
            if(u.getEmail().equalsIgnoreCase(email)){
                return u;
            }
        }
        return null;
    }
}
