package dados;

import modelo.Pet;

/**
 * Teste rápido da frente 3 (leitor e gerador), sem depender do menu nem da ordenação.
 * Para rodar: javac -d out $(find src -name "*.java") && java -cp out dados.TesteDados
 */
public class TesteDados {

    // Lê o arquivo, mostra as três situações do gerador e confere os 10.000 números
    public static void main(String[] args) throws Exception {
        // 1) Leitor: os 50 pets do arquivo
        Pet[] pets = LeitorArquivo.ler("dados/pets.txt");
        System.out.println("Lidos do arquivo: " + pets.length + " pets");
        System.out.println("  primeiro: " + pets[0]);
        System.out.println("  último:   " + pets[pets.length - 1]);

        // 2) Gerador: as três situações com 10 pets, para ver a ordem
        Situacao[] situacoes = {Situacao.ORDENADO, Situacao.INVERSO, Situacao.ALEATORIO};
        for (int s = 0; s < situacoes.length; s++) {
            System.out.println("\n" + situacoes[s] + ":");
            Pet[] gerados = GeradorPets.gerar(10, situacoes[s]);
            for (int i = 0; i < gerados.length; i++) {
                System.out.println("  " + gerados[i]);
            }
        }

        // 3) Gerador com 10.000: confere 8 dígitos e nenhum número repetido
        Pet[] muitos = GeradorPets.gerar(10000, Situacao.ORDENADO);
        boolean ok = true;
        for (int i = 0; i < muitos.length; i++) {
            int numero = muitos[i].getNumero();
            if (numero < 10000000 || numero > 99999999) ok = false;              // 8 dígitos
            if (i > 0 && numero <= muitos[i - 1].getNumero()) ok = false;       // crescente = sem repetição
        }
        System.out.println("\n10.000 pets: números de 8 dígitos e sem repetição? " + (ok ? "SIM" : "NÃO"));
    }
}
