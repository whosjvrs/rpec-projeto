package ordenacao;

import modelo.Pet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

// Teste rápido da parte de ordenação, para rodar sem o menu.
// Usa só os pets reais do dados/pets.txt: ordena com os três algoritmos
// e mostra o modo passo a passo.
// Rodar na pasta rpec-projeto, para achar o dados/pets.txt.
public class TesteOrdenacao {

    // Ordena os pets do arquivo com os três algoritmos e mostra um passo a passo
    public static void main(String[] args) throws IOException {
        // Lê os pets do arquivo
        Pet[] pets = lerPets("dados/pets.txt");
        System.out.println(pets.length + " pets lidos do arquivo");

        // Cada algoritmo ordena uma cópia dos mesmos pets e devolve os contadores
        System.out.println("\n=== Os 3 algoritmos nos pets do arquivo ===");
        mostrar("Bubble Sort   ", new BubbleSort().ordenar(pets));
        mostrar("Insertion Sort", new InsertionSort().ordenar(pets));
        mostrar("Quick Sort    ", new QuickSort().ordenar(pets));

        // Modo passo a passo (só liga com até 10 pets): usa os 4 primeiros do arquivo
        System.out.println("\n=== Passo a passo (Bubble Sort, 4 primeiros pets) ===");
        Pet[] poucos = {pets[0], pets[1], pets[2], pets[3]};
        BubbleSort bubble = new BubbleSort();
        bubble.setNarracao(true);
        bubble.ordenar(poucos);
    }

    // Leitura mínima só para este teste (a leitura oficial é o LeitorArquivo)
    private static Pet[] lerPets(String caminho) throws IOException {
        Pet[] temp = new Pet[200];
        int total = 0;
        try (BufferedReader leitor = new BufferedReader(
                new FileReader(caminho, StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (linha.isBlank()) continue;
                String[] p = linha.split(";");
                temp[total++] = new Pet(Integer.parseInt(p[0]), p[1], p[2], p[3]);
            }
        }
        // Devolve um vetor do tamanho exato
        Pet[] pets = new Pet[total];
        for (int i = 0; i < total; i++) pets[i] = temp[i];
        return pets;
    }

    // Mostra o resultado de um algoritmo
    private static void mostrar(String rotulo, Resultado r) {
        System.out.println(rotulo + " -> comparações: " + r.comparacoes + " | trocas: " + r.trocas);
    }
}
