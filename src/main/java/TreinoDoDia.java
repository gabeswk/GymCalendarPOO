import java.awt.Color;
import java.util.ArrayList;

public class TreinoDoDia {

    private String descricao;
    private Color cor;
    private ArrayList<Exercicio> exercicios = new ArrayList<>();
    private boolean concluido;

    public TreinoDoDia(String descricao, Color cor) {
        this.descricao = descricao;
        this.cor = cor;
    }

    public TreinoDoDia(String descricao) {
        this.descricao = descricao;
        this.cor = Color.GRAY;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
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

    @Override
    public String toString() {
        return descricao;
    }
}
