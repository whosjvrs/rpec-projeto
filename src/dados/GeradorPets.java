package dados;

import modelo.Pet;

import java.util.Random;

/**
 * Gera competidores aleatórios para a Grande Corrida (1.000 e 10.000 pets)
 * e para o teste do fator de carga da tabela hash (pergunta 5).
 *
 * Os números de inscrição têm 8 dígitos e nunca se repetem, sem usar HashSet:
 * eles são gerados já em ordem crescente, sempre somando um salto positivo.
 * A partir dessa lista ordenada saem as três situações:
 *   ORDENADO  -> a lista como foi gerada
 *   INVERSO   -> a lista de trás para frente
 *   ALEATORIO -> a lista embaralhada (Fisher-Yates)
 */
public class GeradorPets {

    // Sorteador com semente fixa: cada execução do programa gera os mesmos pets,
    // então as tabelas do relatório podem ser reproduzidas.
    // (Random é só um sorteador de números, não é estrutura nem ordenação pronta.)
    // pets diferentes a cada execução: tire o 42
    private static final Random sorteio = new Random(42);

    private static final int MENOR_NUMERO = 10000000;   // menor número de 8 dígitos
    private static final int MAIOR_NUMERO = 99999999;   // maior número de 8 dígitos

    // Opções para combinar (tiradas do dados/pets.txt)
    private static final String[] NOMES = {
        "Luna", "Thor", "Pipoca", "Mel", "Zeus", "Amora", "Bob", "Lola", "Paçoca", "Frida",
        "Simba", "Nina", "Pandora", "Chico", "Fred", "Toddy", "Teddy", "Maya", "Bidu", "Safira",
        "Churros", "Mingau", "Olívia", "Rex", "Cacau", "Tobias", "Zoe", "Biscoito", "Nick",
        "Estrela", "Sushi", "Tita", "Apolo"
    };

    private static final String[] ESPECIES = {
        "cão", "gato", "cachorra", "gata", "coelha", "papagaio", "hamster", "calopsita",
        "periquito", "tartaruga", "porquinho-da-índia"
    };

    private static final String[] MANIAS = {
        "dorme em cima do teclado", "late para o aspirador", "rouba meias",
        "mia para a geladeira", "come a ração do irmão", "caça o ponteiro laser",
        "rói o fio do carregador", "imita o toque do celular", "late para a própria sombra",
        "dorme dentro da pia", "esconde ossos embaixo do sofá", "derruba plantas do vaso",
        "persegue o próprio rabo", "acorda o dono às 5h da manhã", "dorme dentro de caixas",
        "tem medo de balões", "cava buracos no sofá", "arranha o sofá"
    };

    // Gera n pets na situação pedida (ALEATORIO, ORDENADO ou INVERSO)
    public static Pet[] gerar(int n, Situacao situacao) {
        Pet[] pets = new Pet[n];

        // Salto máximo entre um número e o próximo: com n saltos, o último número
        // fica no máximo em 10000000 + n * salto, que nunca passa de 99999999.
        int salto = (MAIOR_NUMERO - MENOR_NUMERO) / n;
        int numero = MENOR_NUMERO;

        for (int i = 0; i < n; i++) {
            numero += 1 + sorteio.nextInt(salto);   // sempre aumenta: nunca repete
            pets[i] = new Pet(numero,
                              NOMES[sorteio.nextInt(NOMES.length)],
                              ESPECIES[sorteio.nextInt(ESPECIES.length)],
                              MANIAS[sorteio.nextInt(MANIAS.length)]);
        }
        // Aqui o vetor já está ORDENADO por número de inscrição

        if (situacao == Situacao.INVERSO) {
            inverter(pets);
        } else if (situacao == Situacao.ALEATORIO) {
            embaralhar(pets);
        }
        return pets;
    }

    // Inverte o vetor: troca o primeiro com o último, o segundo com o penúltimo...
    private static void inverter(Pet[] v) {
        for (int i = 0; i < v.length / 2; i++) {
            trocar(v, i, v.length - 1 - i);
        }
    }

    // Embaralha o vetor (Fisher-Yates): de trás para frente, troca cada posição
    // com uma posição sorteada entre 0 e ela mesma
    private static void embaralhar(Pet[] v) {
        for (int i = v.length - 1; i > 0; i--) {
            int sorteada = sorteio.nextInt(i + 1);
            trocar(v, i, sorteada);
        }
    }

    // Troca dois pets de lugar no vetor
    private static void trocar(Pet[] v, int a, int b) {
        Pet aux = v[a];
        v[a] = v[b];
        v[b] = aux;
    }
}
