import dados.LeitorArquivo;
import menu.Menu;
import modelo.Pet;

import java.io.IOException;

/**
 * Ponto de partida do programa: lê os competidores do arquivo e abre o menu.
 * Rodar na pasta rpec-projeto, para o programa achar o dados/pets.txt.
 */
public class Main {

    private static final String ARQUIVO = "dados/pets.txt";

    // Lê os pets do arquivo e entrega para o menu
    public static void main(String[] args) {
        System.out.println("Pet Hash & Sort - Concurso de Fofura");

        Pet[] pets;
        try {
            pets = LeitorArquivo.ler(ARQUIVO);
        } catch (IOException e) {
            System.out.println("Não foi possível ler " + ARQUIVO + ": " + e.getMessage());
            System.out.println("Confira se o programa está rodando na pasta rpec-projeto.");
            return;
        }

        System.out.println(pets.length + " competidores inscritos a partir de " + ARQUIVO);
        new Menu(pets).executar();
    }
}
