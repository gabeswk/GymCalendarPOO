import java.awt.Color;
import java.util.ArrayList;

public class TreinoDoDia {
    private String descricao;         // ex: "Peito" ou "Perna"
    private Color cor;               // cor associada ao tipo do treino
    private ArrayList<Exercicio> exercicios = new ArrayList<>();
    private boolean concluido;
    public TreinoDoDia(String descricao, Color cor) {
        this.descricao = descricao;
        this.cor = cor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public boolean isConcluido() {
        return concluido;
    }
    public Color getCor() {
        return cor;
    }

    public ArrayList<Exercicio> getExercicios() {
        return exercicios;
    }

    public void adicionarExercicio(Exercicio ex) {
        exercicios.add(ex);
    }

    public void removerExercicio(int index) {
        if (index >= 0 && index < exercicios.size()) {
            exercicios.remove(index);
        }
    }
    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }
    @Override
    public String toString() {
        return descricao;
    }
}
