package modelo;

import java.text.Normalizer;

public class Pet {
    private int numero;      // nº de inscrição, 8 dígitos, único
    private String nome;
    private String especie;
    private String mania;

    public Pet(int numero, String nome, String especie, String mania) {
        this.numero = numero;
        this.nome = nome;
        this.especie = especie;
        this.mania = mania;
    }

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

    @Override
    public String toString() {
        return nome + " (" + numero + ") - " + especie + ", " + mania;
    }
}
