package ordenacao;

import modelo.Pet;

// Quick Sort: escolhe um pet como pivô, manda os menores para a esquerda e os maiores
// para a direita, e repete o processo em cada lado (divisão e conquista).
// O pivô é sempre o último pet do trecho. Com a fila já ordenada isso é o pior caso.
// Trocas de um pet com ele mesmo também são contadas.
public class QuickSort extends Ordenador {

    private boolean narrar;   // narração ligada nesta execução?
    private int partes;       // quantas partições já foram feitas (usado na narração)

    @Override
    public Resultado ordenar(Pet[] original) {
        // 1. Trabalha numa cópia, para não mexer no vetor original
        Pet[] v = copiar(original);

        // 2. Zera os contadores antes de começar
        comparacoes = 0;
        trocas = 0;
        partes = 0;

        // 3. Só narra se a narração estiver ligada e houver até 10 pets
        narrar = narracao && v.length <= Narrador.LIMITE_PETS;

        // 4. Começa a medir o tempo
        long inicio = System.nanoTime();

        // 5. Ordena o vetor inteiro (da posição 0 até a última)
        quickSort(v, 0, v.length - 1);

        // 6. Para o cronômetro e devolve os números
        long tempo = System.nanoTime() - inicio;
        return new Resultado(comparacoes, trocas, tempo);
    }

    // Ordena o trecho do vetor entre inicio e fim
    private void quickSort(Pet[] v, int inicio, int fim) {
        // Trecho com 0 ou 1 pet já está ordenado: não faz nada
        if (inicio < fim) {
            // Coloca o pivô no lugar definitivo e descobre onde ele ficou
            int posPivot = particionar(v, inicio, fim);

            // Repete o processo nos pets menores (esquerda) e maiores (direita) que o pivô
            quickSort(v, inicio, posPivot - 1);
            quickSort(v, posPivot + 1, fim);
        }
    }

    // Separa o trecho em menores e maiores que o pivô e devolve a posição final do pivô
    private int particionar(Pet[] v, int inicio, int fim) {
        // O pivô é o último pet do trecho
        Pet pivot = v[fim];

        // i marca onde termina o grupo dos menores (começa vazio)
        int i = inicio - 1;

        if (narrar) {
            partes++;
            Narrador.cabecalho("Quick Sort", "partição", partes);
            Narrador.pivo(pivot);
        }

        // j percorre todos os pets do trecho, menos o pivô
        for (int j = inicio; j < fim; j++) {
            // O pet atual é menor que o pivô? (o mesmo que perguntar "pivô > pet?")
            boolean menor = maior(pivot, v[j]);
            if (narrar) Narrador.comparacaoPivo(v[j], pivot, menor);

            // Se for menor, entra no grupo dos menores
            if (menor) {
                i++;
                trocar(v, i, j);
            }
        }

        // O pivô vai para o lugar definitivo, logo depois do grupo dos menores
        trocar(v, i + 1, fim);

        if (narrar) {
            Narrador.pivoNoLugar(pivot, i + 1);
            Narrador.fimEtapa("da partição " + partes, comparacoes, trocas);
        }
        return i + 1;
    }
}
