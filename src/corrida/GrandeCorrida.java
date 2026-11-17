package corrida;

import dados.GeradorPets;
import dados.Situacao;
import modelo.Pet;
import ordenacao.Ordenador;
import ordenacao.Resultado;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * A Grande Corrida: compara os algoritmos de ordenação medindo tempo,
 * comparações e trocas em 3 tamanhos (50, 1.000 e 10.000 pets) e
 * 3 situações (aleatório, já ordenado, ordem inversa).
 *
 * A corrida não conhece os algoritmos pelo nome: ela recebe um vetor de
 * Ordenador. Quem chama (o menu) decide quais algoritmos entram, por exemplo:
 *
 *   GrandeCorrida.executar(
 *       new Ordenador[]{ new BubbleSort(), new InsertionSort(), new QuickSort() },
 *       new String[]{ "Bubble Sort", "Insertion Sort", "Quick Sort" });
 *
 * Regras de medição (decididas pelo grupo):
 *   - os algoritmos ordenam o MESMO vetor gerado (comparação justa);
 *   - 1 rodada de aquecimento (descartada) + média do tempo de 3 rodadas;
 *   - narração sempre desligada (senão o println domina o tempo).
 */
public class GrandeCorrida {

    // tamanhos da corrida: mude aqui e ajuste os títulos em imprimirTabela
    private static final int[] TAMANHOS = {50, 1000, 10000};
    private static final Situacao[] SITUACOES = {Situacao.ALEATORIO, Situacao.ORDENADO, Situacao.INVERSO};
    private static final String[] NOMES_SITUACOES = {"Aleatório", "Já ordenado", "Ordem inversa"};
    // mais rodadas = média de tempo mais estável
    private static final int RODADAS = 3;

    private static final long ESTOUROU = -1;   // marca a medição que deu StackOverflowError
    private static final String ARQUIVO_CSV = "relatorio/grande-corrida.csv";
    private static final Locale BRASIL = Locale.forLanguageTag("pt-BR");   // 1.000,5 em vez de 1,000.5

    // Roda as medições de todos os algoritmos e imprime as 3 tabelas
    public static void executar(Ordenador[] algoritmos, String[] nomes) {
        // Uma linha por algoritmo e situação (na ordem da tabela do enunciado),
        // uma coluna por tamanho. Ex.: 3 algoritmos x 3 situações = 9 linhas.
        int linhas = algoritmos.length * SITUACOES.length;
        long[][] tempos = new long[linhas][TAMANHOS.length];
        long[][] comparacoes = new long[linhas][TAMANHOS.length];
        long[][] trocas = new long[linhas][TAMANHOS.length];

        System.out.println("\n=== A GRANDE CORRIDA DO DESFILE ===");
        System.out.println("Cada algoritmo: 1 rodada de aquecimento + média de " + RODADAS + " rodadas.\n");

        for (int t = 0; t < TAMANHOS.length; t++) {
            for (int s = 0; s < SITUACOES.length; s++) {
                // UM vetor por tamanho e situação: todos os algoritmos recebem o mesmo
                Pet[] pets = GeradorPets.gerar(TAMANHOS[t], SITUACOES[s]);

                for (int a = 0; a < algoritmos.length; a++) {
                    int linha = a * SITUACOES.length + s;
                    System.out.print("Correndo: " + nomes[a] + " · " + NOMES_SITUACOES[s]
                            + " · n = " + TAMANHOS[t] + " ... ");

                    long[] medicao = medir(algoritmos[a], pets);
                    tempos[linha][t] = medicao[0];
                    comparacoes[linha][t] = medicao[1];
                    trocas[linha][t] = medicao[2];

                    System.out.println(medicao[0] == ESTOUROU ? "estourou a pilha!" : "ok");
                }
            }
        }

        imprimirTabela("Tempo (ms, média de " + RODADAS + " rodadas)", tempos, nomes, true);
        imprimirTabela("Comparações", comparacoes, nomes, false);
        imprimirTabela("Trocas (no Insertion Sort, deslocamentos)", trocas, nomes, false);
        exportarCsv(tempos, comparacoes, trocas, nomes);
    }

    // Mede um algoritmo sobre um vetor: devolve {tempo médio em ns, comparações, trocas}.
    // Se a recursão estourar a pilha (Quick Sort no pior caso), devolve ESTOUROU nos três.
    private static long[] medir(Ordenador algoritmo, Pet[] pets) {
        algoritmo.setNarracao(false);
        try {
            algoritmo.ordenar(copiar(pets));       // aquecimento: o resultado é descartado

            long somaTempos = 0;
            Resultado resultado = null;
            for (int r = 0; r < RODADAS; r++) {
                resultado = algoritmo.ordenar(copiar(pets));
                somaTempos += resultado.tempoNs;
            }
            // Comparações e trocas são iguais em todas as rodadas; só o tempo varia
            return new long[]{somaTempos / RODADAS, resultado.comparacoes, resultado.trocas};

        } catch (StackOverflowError e) {
            // Pior caso do Quick Sort (vetor ordenado ou invertido com pivô no fim):
            // a recursão fica com ~n chamadas empilhadas. Registramos e seguimos.
            return new long[]{ESTOUROU, ESTOUROU, ESTOUROU};
        }
    }

    // Copia o vetor antes de cada rodada. O ordenar já trabalha numa cópia,
    // mas copiar aqui também garante que nenhuma rodada receba um vetor já ordenado
    // pela rodada anterior. A cópia fica fora da medição de tempo (que é feita no ordenar).
    private static Pet[] copiar(Pet[] original) {
        Pet[] copia = new Pet[original.length];
        for (int i = 0; i < original.length; i++) {
            copia[i] = original[i];
        }
        return copia;
    }

    // Imprime uma tabela: uma linha por algoritmo e situação, uma coluna por tamanho
    private static void imprimirTabela(String titulo, long[][] valores, String[] nomes, boolean ehTempo) {
        System.out.println("\n" + titulo);
        System.out.println(String.format("%-16s %-14s %16s %16s %16s",
                // títulos das colunas: acompanham o vetor TAMANHOS
                "Algoritmo", "Situação", "n = 50", "n = 1.000", "n = 10.000"));
        System.out.println("-".repeat(82));

        for (int linha = 0; linha < valores.length; linha++) {
            int a = linha / SITUACOES.length;   // qual algoritmo
            int s = linha % SITUACOES.length;   // qual situação
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-16s %-14s", nomes[a], NOMES_SITUACOES[s]));
            for (int t = 0; t < TAMANHOS.length; t++) {
                sb.append(String.format(" %16s", formatar(valores[linha][t], ehTempo)));
            }
            System.out.println(sb);
        }
    }

    // Formata um valor da tabela: tempo em ms com 3 casas, contadores com separador de milhar
    private static String formatar(long valor, boolean ehTempo) {
        if (valor == ESTOUROU) return "estouro";
        if (ehTempo) return String.format(BRASIL, "%.3f", valor / 1_000_000.0);   // ns -> ms
        return String.format(BRASIL, "%,d", valor);
    }

    // Salva as 27 medições em relatorio/grande-corrida.csv (abre no Google Sheets / Excel)
    private static void exportarCsv(long[][] tempos, long[][] comparacoes, long[][] trocas, String[] nomes) {
        try (PrintWriter csv = new PrintWriter(new FileWriter(ARQUIVO_CSV, StandardCharsets.UTF_8))) {
            csv.println("algoritmo;situacao;n;tempo_ms;comparacoes;trocas");
            for (int linha = 0; linha < tempos.length; linha++) {
                int a = linha / SITUACOES.length;
                int s = linha % SITUACOES.length;
                for (int t = 0; t < TAMANHOS.length; t++) {
                    csv.println(nomes[a] + ";" + NOMES_SITUACOES[s] + ";" + TAMANHOS[t] + ";"
                            + formatarCsv(tempos[linha][t], true) + ";"
                            + formatarCsv(comparacoes[linha][t], false) + ";"
                            + formatarCsv(trocas[linha][t], false));
                }
            }
            System.out.println("\nTabelas salvas em " + ARQUIVO_CSV);
        } catch (IOException e) {
            System.out.println("\nNão foi possível salvar " + ARQUIVO_CSV + ": " + e.getMessage());
        }
    }

    // Formata um valor para o CSV (sem separador de milhar, para a planilha entender como número)
    private static String formatarCsv(long valor, boolean ehTempo) {
        if (valor == ESTOUROU) return "estouro";
        if (ehTempo) return String.format(BRASIL, "%.3f", valor / 1_000_000.0);
        return String.valueOf(valor);
    }
}
