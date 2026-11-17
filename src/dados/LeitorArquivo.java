package dados;

import modelo.Pet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Lê o arquivo de competidores (dados/pets.txt) e devolve um vetor de Pet.
 * Formato de cada linha: numero;nome;especie;mania
 * O arquivo é lido em UTF-8 por causa dos acentos (Paçoca, cão, Olívia...).
 */
public class LeitorArquivo {

    // Lê o arquivo do caminho informado e devolve um vetor com todos os pets
    public static Pet[] ler(String caminho) throws IOException {
        Pet[] pets = new Pet[64];   // começa com espaço de sobra e dobra se precisar
        int total = 0;

        try (BufferedReader leitor = new BufferedReader(
                new FileReader(caminho, StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (linha.isBlank()) continue;           // pula linhas vazias

                // novo pet no arquivo: uma linha numero;nome;especie;mania no fim do pets.txt
                String[] partes = linha.split(";");
                if (partes.length < 4) {                 // linha mal formada: avisa e pula
                    System.out.println("Linha ignorada (formato errado): " + linha);
                    continue;
                }

                // Vetor cheio? Dobra o tamanho, como no redimensionar da tabela hash do material
                if (total == pets.length) {
                    pets = dobrar(pets);
                }

                pets[total] = new Pet(Integer.parseInt(partes[0].trim()),
                                      partes[1].trim(),
                                      partes[2].trim(),
                                      partes[3].trim());
                total++;
            }
        }

        // Devolve um vetor do tamanho exato (sem posições null no fim)
        Pet[] resultado = new Pet[total];
        for (int i = 0; i < total; i++) {
            resultado[i] = pets[i];
        }
        return resultado;
    }

    // Cria um vetor com o dobro do tamanho e copia os pets para ele
    private static Pet[] dobrar(Pet[] antigo) {
        Pet[] novo = new Pet[antigo.length * 2];
        for (int i = 0; i < antigo.length; i++) {
            novo[i] = antigo[i];
        }
        return novo;
    }
}
