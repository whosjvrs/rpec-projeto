package ordenacao;

import modelo.Pet;

// Guarda as frases do Concurso de Fofura para o modo passo a passo.
// Os algoritmos só chamam estes métodos quando a narração está ligada.
public class Narrador {

    // A narração só liga com até 10 pets (com mais, imprimiria linhas demais)
    // mais pets no passo a passo? aumente aqui
    public static final int LIMITE_PETS = 10;

    // Título de uma etapa, ex.: "♥ Bubble Sort · passagem 1"
    public static void cabecalho(String algoritmo, String etapa, int numero) {
        System.out.println("♥ " + algoritmo + " · " + etapa + " " + numero);
    }

    // Bubble: narra a comparação entre dois vizinhos e se eles trocam de lugar
    public static void comparacao(Pet a, Pet b, boolean trocam) {
        // inverteu a ordem no Ordenador? troque o " > " das três frases por " < "
        System.out.print("  " + descrever(a) + " > " + descrever(b) + "? ");
        if (trocam) {
            System.out.println("SIM → trocam de lugar na fila do desfile!");
        } else {
            System.out.println("NÃO → " + a.getNome() + " segue desfilando no lugar.");
        }
    }

    // Insertion: narra a comparação do vizinho com o pet que está procurando seu lugar
    public static void comparacaoInsercao(Pet vizinho, Pet chave, boolean desloca) {
        System.out.print("  " + descrever(vizinho) + " > " + descrever(chave) + "? ");
        if (desloca) {
            System.out.println("SIM → " + vizinho.getNome() + " dá uma casa de espaço na fila.");
        } else {
            System.out.println("NÃO → " + chave.getNome() + " já achou o seu lugar.");
        }
    }

    // Quick: anuncia qual pet foi escolhido como pivô
    public static void pivo(Pet pivo) {
        System.out.println("  pivô da vez: " + descrever(pivo));
    }

    // Quick: narra a comparação de um pet com o pivô
    public static void comparacaoPivo(Pet pet, Pet pivo, boolean vaiParaEsquerda) {
        System.out.print("  " + descrever(pivo) + " > " + descrever(pet) + "? ");
        if (vaiParaEsquerda) {
            System.out.println("SIM → " + pet.getNome() + " vai para o grupo da frente!");
        } else {
            System.out.println("NÃO → " + pet.getNome() + " fica no grupo de trás.");
        }
    }

    // Quick: narra a posição em que o pivô ficou definitivamente
    public static void pivoNoLugar(Pet pivo, int posicao) {
        System.out.println("  " + pivo.getNome() + " ocupa o lugar definitivo no desfile: posição " + posicao);
    }

    // Fim de uma etapa, mostrando os contadores até agora.
    // O texto vem pronto, ex.: "da passagem 1" → "fim da passagem 1 → comparações: 3 | trocas: 3"
    public static void fimEtapa(String etapa, long comparacoes, long trocas) {
        System.out.println("  fim " + etapa
                + " → comparações: " + comparacoes + " | trocas: " + trocas);
    }

    // Escreve o pet como "Luna (60218841)"
    private static String descrever(Pet p) {
        // ordenando por outro campo? mostre esse campo aqui no lugar do número
        return p.getNome() + " (" + p.getNumero() + ")";
    }
}
