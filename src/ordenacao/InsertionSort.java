package ordenacao;

import modelo.Pet;

// Insertion Sort: pega um pet de cada vez (a "chave") e o desliza para a esquerda
// até achar o lugar dele, como quem arruma cartas na mão.
// Cada deslocamento é contado como troca.
public class InsertionSort extends Ordenador {

    // Ordena uma cópia do vetor com o Insertion Sort e devolve comparações, deslocamentos e tempo
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

        // 5. O primeiro pet já é uma fila ordenada sozinho; cada volta encaixa o próximo
        for (int i = 1; i < v.length; i++) {
            if (narrar) Narrador.cabecalho("Insertion Sort", "passo", i);

            // A chave é o pet que vai procurar o lugar dele
            Pet chave = v[i];
            int j = i - 1;

            // 6. Anda para a esquerda enquanto houver pets (j >= 0) com número maior que a chave
            while (j >= 0) {
                boolean desloca = maior(v[j], chave);
                if (narrar) Narrador.comparacaoInsercao(v[j], chave, desloca);

                // Achou um pet menor: a chave já está no lugar certo
                if (!desloca) break;

                // Empurra o pet maior uma casa para a direita e conta o deslocamento
                v[j + 1] = v[j];
                trocas++;
                j--;
            }

            // 7. Coloca a chave no espaço que sobrou
            v[j + 1] = chave;

            if (narrar) Narrador.fimEtapa("do passo " + i, comparacoes, trocas);
        }

        // 8. Para o cronômetro e devolve os números
        long tempo = System.nanoTime() - inicio;
        ordenado = v;   // guarda o vetor ordenado para o menu mostrar
        return new Resultado(comparacoes, trocas, tempo);
    }
}
