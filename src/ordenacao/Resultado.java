package ordenacao;

public class Resultado {
    public final long comparacoes;
    public final long trocas;
    public final long tempoNs;

    public Resultado(long comparacoes, long trocas, long tempoNs) {
        this.comparacoes = comparacoes;
        this.trocas = trocas;
        this.tempoNs = tempoNs;
    }
}
