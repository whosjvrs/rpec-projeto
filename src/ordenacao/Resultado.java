package ordenacao;

/**
 * O que um algoritmo de ordenação devolve: quantas comparações e trocas fez
 * e quanto tempo levou (em nanossegundos). A Grande Corrida monta as tabelas com isso.
 */
public class Resultado {
    public final long comparacoes;
    public final long trocas;
    public final long tempoNs;

    // Guarda os três números de uma ordenação
    public Resultado(long comparacoes, long trocas, long tempoNs) {
        this.comparacoes = comparacoes;
        this.trocas = trocas;
        this.tempoNs = tempoNs;
    }
}
