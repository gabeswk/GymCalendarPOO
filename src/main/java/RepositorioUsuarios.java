import java.io.*;
import java.util.ArrayList;

public class RepositorioUsuarios {
    private static final String ARQUIVO = "usuarios.txt";
    private static ArrayList<Usuario> usuarios = new ArrayList<>();

    // Bloco static: Executa assim que a classe é carregada pela primeira vez
    static {
        carregarUsuarios();
    }

    public static void adicionar(Usuario u) {
        usuarios.add(u);
        salvarNoArquivo(); // Salva sempre que adicionar um novo
    }

    public static Usuario buscarPorEmail(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }

    // Método para salvar a senha alterada (usado no EsqueciSenha)
    public static void atualizarArquivo() {
        salvarNoArquivo();
    }

    private static void salvarNoArquivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Usuario u : usuarios) {
                // Formato: nome;email;senha
                bw.write(u.getNome() + ";" + u.getEmail() + ";" + u.getSenha());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    private static void carregarUsuarios() {
        File file = new File(ARQUIVO);
        if (!file.exists()) return; // Se o arquivo não existe, não faz nada

        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 3) {
                    usuarios.add(new Usuario(dados[0], dados[1], dados[2]));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }
}