package ordenacao;

import modelo.Pet;

public abstract class Ordenador {
    protected boolean narracao = false;
    protected long comparacoes;
    protected long trocas;

    public void setNarracao(boolean ligada) { narracao = ligada; }

    // Ordena uma CÓPIA do vetor por número de inscrição e devolve os contadores
    public abstract Resultado ordenar(Pet[] vetor);
}
