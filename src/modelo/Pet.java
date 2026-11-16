package modelo;

import java.text.Normalizer;

/**
 * A ficha de um competidor do Concurso de Fofura (base comum de todas as frentes).
 * A tabela hash guarda o pet pela CHAVE (nome normalizado) e a ordenação
 * coloca os pets em ordem pelo NÚMERO de inscrição.
 */
public class Pet {
    private int numero;      // nº de inscrição, 8 dígitos, único
    private String nome;
    private String especie;
    private String mania;

    // Cria a ficha com os quatro dados que vêm de cada linha do pets.txt
    public Pet(int numero, String nome, String especie, String mania) {
        this.numero = numero;
        this.nome = nome;
        this.especie = especie;
        this.mania = mania;
    }

    // Getters: devolvem os dados da ficha (o número é por onde se ORDENA)
    public int getNumero() { return numero; }
    public String getNome() { return nome; }
    public String getEspecie() { return especie; }
    public String getMania() { return mania; }

    // Nome em maiúsculas e sem acento: "Paçoca" -> "PACOCA"
    public String getChave() {
        String semAcento = Normalizer.normalize(nome, Normalizer.Form.NFD)
                                     .replaceAll("\\p{M}", "");
        return semAcento.toUpperCase();
    }

    // Mostra o pet como "Paçoca (65502291) - gato, caça o ponteiro laser"
    @Override
    public String toString() {
        return nome + " (" + numero + ") - " + especie + ", " + mania;
    }
}
