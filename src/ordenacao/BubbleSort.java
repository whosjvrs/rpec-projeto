package ordenacao;

import modelo.Pet;

// Bubble Sort: compara pets vizinhos e troca quando estão fora de ordem.
// A cada passagem, o maior número "flutua" até o fim da fila.
public class BubbleSort extends Ordenador {

    // Ordena uma cópia do vetor com o Bubble Sort e devolve comparações, trocas e tempo
    @Override
    public Resultado ordenar(Pet[] original) {
        // 1. Trabalha numa cópia, para não mexer no vetor original
        Pet[] v = copiar(original);

        // 2. Zera os contadores antes de começar
        comparacoes = 0;
        trocas = 0;

        // 3. Só narra se a narração estiver ligada e houver até 10 pets
        boolean narrar = narracao && v.length <= Narrador.LIMITE_PETS;

        // 4. Começa a medir o tempo
        long inicio = System.nanoTime();

        int n = v.length;

        // 5. Cada volta do for de fora é uma passagem pela fila
        for (int i = 0; i < n - 1; i++) {
            if (narrar) Narrador.cabecalho("Bubble Sort", "passagem", i + 1);

            // Guarda se houve alguma troca nesta passagem
            boolean trocou = false;

            // 6. Compara cada par de vizinhos (os últimos i pets já estão no lugar)
            for (int j = 0; j < n - 1 - i; j++) {
                // O da esquerda tem número maior que o da direita?
                boolean fora = maior(v[j], v[j + 1]);
                if (narrar) Narrador.comparacao(v[j], v[j + 1], fora);

                // Se sim, os dois trocam de lugar
                if (fora) {
                    trocar(v, j, j + 1);
                    trocou = true;
                }
            }

            if (narrar) Narrador.fimEtapa("da passagem " + (i + 1), comparacoes, trocas);

            // 7. Parada antecipada: passagem sem nenhuma troca = fila já ordenada
            // sem parada antecipada: apague este if (melhor caso volta a ser O(n²))
            if (!trocou) break;
        }

        // 8. Para o cronômetro e devolve os números
        long tempo = System.nanoTime() - inicio;
        ordenado = v;   // guarda o vetor ordenado para o menu mostrar
        return new Resultado(comparacoes, trocas, tempo);
    }
}
