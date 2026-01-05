import java.awt.Color;
import java.util.ArrayList;

public class TreinoDoDia {
    private String descricao;         // ex: "Peito" ou "Perna"
    private Color cor;               // cor associada ao tipo do treino
    private ArrayList<Exercicio> exercicios = new ArrayList<>();

    public TreinoDoDia(String descricao, Color cor) {
        this.descricao = descricao;
        this.cor = cor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) { this.descricao = descricao; }


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
