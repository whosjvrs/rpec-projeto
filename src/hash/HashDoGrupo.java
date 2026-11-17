package hash;

/**
 * Função hash 2 (criada pelo grupo): polinomial com base 31.
 * A cada letra, multiplica o valor acumulado por 31 e soma o código da letra.
 * Assim a ORDEM das letras importa: AMOR e ROMA caem em baldes diferentes.
 * É a mesma conta do hashString do material da professora.
 */
public class HashDoGrupo implements FuncaoHash {

    // Para cada letra: h = h * 31 + código da letra. No fim, o resto da divisão por m
    @Override
    public int calcular(String chave, int m) {
        int h = 0;
        for (int i = 0; i < chave.length(); i++) {
            // outra base? troque o 31 (ex.: 37)
            h = 31 * h + chave.charAt(i);
        }
        return Math.floorMod(h, m);   // floorMod: nunca dá negativo
    }

    // Nome mostrado no menu (mudou a base? mude aqui também)
    @Override
    public String nome() { return "base 31"; }
}