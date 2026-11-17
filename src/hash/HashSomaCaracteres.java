package hash;

/**
 * Função hash 1: soma os códigos dos caracteres da chave.
 * É a mesma função do material de apoio da professora.
 * Fraqueza: anagramas (AMOR e ROMA) sempre caem no mesmo balde.
 */
public class HashSomaCaracteres implements FuncaoHash {

    // Soma o código de cada letra e devolve o resto da divisão por m
    @Override
    public int calcular(String chave, int m) {
        int soma = 0;
        for (int i = 0; i < chave.length(); i++) {
            soma += chave.charAt(i);   // charAt devolve o código da letra
        }
        // outra conta? ex.: multiplicar a soma por um número antes do % m
        return soma % m;
    }

    // Nome mostrado no menu
    @Override
    public String nome() { return "soma dos caracteres"; }
}