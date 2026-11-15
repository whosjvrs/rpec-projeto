package ordenacao;

import modelo.Pet;

// Classe-mãe dos três algoritmos de ordenação.
// Guarda os contadores e três ajudantes que fazem a contagem sozinhos:
// todo maior(...) conta uma comparação e todo trocar(...) conta uma troca.
public abstract class Ordenador {
    protected boolean narracao = false;   // modo passo a passo ligado ou desligado
    protected long comparacoes;           // quantas comparações o algoritmo fez
    protected long trocas;                // quantas trocas (ou deslocamentos) o algoritmo fez

    // Liga ou desliga o modo passo a passo
    public void setNarracao(boolean ligada) { narracao = ligada; }

    // Cada algoritmo implementa este método: ordena uma CÓPIA do vetor
    // por número de inscrição e devolve os contadores e o tempo
    public abstract Resultado ordenar(Pet[] vetor);

    // Faz uma cópia do vetor, para o original nunca ser alterado
    // (assim os três algoritmos ordenam exatamente os mesmos dados)
    protected Pet[] copiar(Pet[] original) {
        Pet[] copia = new Pet[original.length];
        for (int i = 0; i < original.length; i++) {
            copia[i] = original[i];
        }
        return copia;
    }

    // Compara dois pets pelo número de inscrição e CONTA a comparação
    protected boolean maior(Pet a, Pet b) {
        comparacoes++;
        return a.getNumero() > b.getNumero();
    }

    // Troca dois pets de lugar no vetor e CONTA a troca
    protected void trocar(Pet[] v, int a, int b) {
        trocas++;
        Pet aux = v[a];
        v[a] = v[b];
        v[b] = aux;
    }
}
