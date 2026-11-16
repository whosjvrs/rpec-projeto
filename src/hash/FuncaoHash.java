package hash;

/**
 * O "contrato" de uma função hash: transformar a chave num índice de 0 a m-1.
 * Existem duas implementações (HashSomaCaracteres e HashDoGrupo), e a TabelaHash
 * funciona com qualquer uma delas. É isso que permite comparar as duas (pergunta 4).
 */
public interface FuncaoHash {
    // Devolve um índice de 0 a m-1
    int calcular(String chave, int m);
}
