public class Exercicio {

    private String nome;
    private int series;
    private int repeticoes;

    public Exercicio(String nome, int series, int repeticoes) {
        this.nome = nome;
        this.series = series;
        this.repeticoes = repeticoes;
    }

    public Exercicio(String nome) {
        this.nome = nome;
        this.series = 3;
        this.repeticoes = 10;
    }


    public String getNome() { return nome; }
    public int getSeries() { return series; }
    public int getRepeticoes() { return repeticoes; }

    public void setNome(String nome) { this.nome = nome; }
    public void setSeries(int series) { this.series = series; }
    public void setRepeticoes(int repeticoes) { this.repeticoes = repeticoes; }

    @Override
    public String toString() {
        return nome + " - " + series + "x" + repeticoes;
    }
}
