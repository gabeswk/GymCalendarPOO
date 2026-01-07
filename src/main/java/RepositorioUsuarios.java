import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class RepositorioUsuarios {
    private static final String ARQUIVO = "usuarios.txt";
    private static ArrayList<Usuario> usuarios = new ArrayList<>();

    static {
        carregarUsuarios();
    }

    public static void adicionar(Usuario u) {
        usuarios.add(u);
        try {
            salvarNoArquivo();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro crítico ao salvar dados: " + e.getMessage(), "Erro de Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void atualizarArquivo() {
        try {
            salvarNoArquivo();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível atualizar o arquivo.");
        }
    }

    // Adicionamos 'throws IOException' para quem chama o método tratar o erro
    private static void salvarNoArquivo() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Usuario u : usuarios) {
                bw.write(u.getNome() + ";" + u.getEmail() + ";" + u.getSenha());
                bw.newLine();
            }
        } catch (IOException e) {
            // Log interno do erro antes de relançar
            System.err.println("Falha na escrita: " + e.getMessage());
            throw e;
        }
    }

    private static void carregarUsuarios() {
        File file = new File(ARQUIVO);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 3) {
                    usuarios.add(new Usuario(dados[0], dados[1], dados[2]));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado, será criado um novo ao salvar.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler banco de dados de usuários.");
        } catch (Exception e) {
            System.err.println("Erro desconhecido: " + e.getMessage());
        }
    }

    public static Usuario buscarPorEmail(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) return u;
        }
        return null;
    }
}