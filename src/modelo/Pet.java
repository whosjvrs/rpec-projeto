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

    // Chave da tabela hash: o nome normalizado ("Paçoca" -> "PACOCA")
    public String getChave() {
        // agrupar por outro campo na tabela? troque nome por especie aqui
        return normalizar(nome);
    }

    // Deixa um texto em maiúsculas e sem acento. Usado aqui na chave e na busca
    // da TabelaHash, para o nome digitado e o nome guardado ficarem iguais.
    public static String normalizar(String texto) {
        String semAcento = Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
                                     .replaceAll("\\p{M}", "");
        return semAcento.toUpperCase();
    }

    // Mostra o pet como "Paçoca (65502291) - gato, caça o ponteiro laser"
    @Override
    public String toString() {
        // muda como o pet aparece em todas as telas
        return nome + " (" + numero + ") - " + especie + ", " + mania;
    }
}
