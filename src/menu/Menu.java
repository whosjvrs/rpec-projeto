package menu;

import corrida.GrandeCorrida;
import hash.FuncaoHash;
import hash.HashDoGrupo;
import hash.HashSomaCaracteres;
import hash.TabelaHash;
import modelo.Pet;
import ordenacao.BubbleSort;
import ordenacao.InsertionSort;
import ordenacao.Narrador;
import ordenacao.Ordenador;
import ordenacao.QuickSort;
import ordenacao.Resultado;

import java.util.Locale;
import java.util.Scanner;

/**
 * Menu de console do Concurso de Fofura. Liga as três partes do projeto:
 *   - a mesa de inscrições (tabela hash)                   -> opções 1 a 5;
 *   - a ordem do desfile (ordenação por nº de inscrição)  -> opções 6 e 7;
 *   - a Grande Corrida (comparação dos algoritmos)          -> opção 8.
 *
 * Além da tabela, o menu guarda um VETOR com todos os inscritos, porque a
 * ordenação trabalha com Pet[] e não com a tabela hash (que não tem ordem).
 * Por isso, inscrever e cancelar mexem nos dois: na tabela e no vetor.
 */
public class Menu {

    // tamanho da mesa: mude aqui (de preferência um primo)
    private static final int TAMANHO_MESA = 53;            // decisão do grupo (guia 8.4)
    // função hash da mesa: troque por new HashSomaCaracteres() (os textos acompanham)
    private static final FuncaoHash FUNCAO_MESA = new HashDoGrupo();
    // tamanhos comparados na opção 5
    private static final int[] TAMANHOS_TESTE = {13, 17, 53};
    private static final int MENOR_NUMERO = 10000000;      // nº de inscrição tem 8 dígitos
    private static final int MAIOR_NUMERO = 99999999;
    private static final Locale BRASIL = Locale.forLanguageTag("pt-BR");   // 0,037 em vez de 0.037

    private final TabelaHash mesa;   // a mesa de inscrições (busca por nome)
    private Pet[] inscritos;         // os mesmos pets num vetor (para ordenar)
    private int total;               // quantos pets estão no vetor
    private final Scanner entrada = new Scanner(System.in);

    // Os três algoritmos, na mesma ordem das tabelas do enunciado
    // novo algoritmo? classe que estende Ordenador + o nome na lista de baixo
    private final Ordenador[] algoritmos = {new BubbleSort(), new InsertionSort(), new QuickSort()};
    private final String[] nomesAlgoritmos = {"Bubble Sort", "Insertion Sort", "Quick Sort"};

    // Monta a mesa e o vetor com os pets lidos do arquivo
    public Menu(Pet[] pets) {
        mesa = new TabelaHash(TAMANHO_MESA, FUNCAO_MESA);
        inscritos = new Pet[pets.length + 16];   // espaço de sobra para novas inscrições
        total = 0;
        for (int i = 0; i < pets.length; i++) {
            mesa.inserir(pets[i]);
            adicionarAoVetor(pets[i]);
        }
    }

    // Laço principal: mostra o menu, lê a opção e chama o que ela pede
    public void executar() {
        int opcao;
        do {
            mostrarMenu();
            opcao = lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1: inscrever(); break;
                case 2: buscar(); break;
                case 3: cancelar(); break;
                case 4: mostrarMesa(); break;
                case 5: estatisticas(); break;
                case 6: organizarDesfile(); break;
                case 7: desfilePassoAPasso(); break;
                case 8: grandeCorrida(); break;
                case 0: System.out.println("\nConcurso encerrado! Obrigado a todos os competidores ♥"); break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    // Desenha o menu dentro de uma moldura (o format alinha a borda da direita)
    private void mostrarMenu() {
        String borda = "═".repeat(42);
        System.out.println("\n╔" + borda + "╗");
        System.out.println(String.format("║ %-40s ║", "         ♥ PET HASH & SORT ♥"));
        System.out.println(String.format("║ %-40s ║", "          Concurso de Fofura"));
        System.out.println("╠" + borda + "╣");
        System.out.println(String.format("║ %-40s ║", "1 - Inscrever competidor"));
        System.out.println(String.format("║ %-40s ║", "2 - Buscar competidores por nome"));
        System.out.println(String.format("║ %-40s ║", "3 - Cancelar inscrição (nº de inscrição)"));
        System.out.println(String.format("║ %-40s ║", "4 - Mostrar mesa de inscrições"));
        System.out.println(String.format("║ %-40s ║", "5 - Estatísticas da mesa"));
        System.out.println(String.format("║ %-40s ║", "6 - Organizar o desfile"));
        System.out.println(String.format("║ %-40s ║", "7 - Desfile passo a passo"));
        System.out.println(String.format("║ %-40s ║", "8 - A Grande Corrida"));
        System.out.println(String.format("║ %-40s ║", "0 - Encerrar o concurso"));
        System.out.println("╚" + borda + "╝");
    }

    // ---------------------------------------------------------------
    // OPÇÕES 1 a 5: a mesa de inscrições (tabela hash)
    // ---------------------------------------------------------------

    // Opção 1: lê os dados de um novo competidor e inscreve na mesa e no vetor
    private void inscrever() {
        System.out.println("\n♥ Nova inscrição");
        int numero = lerInteiro("Nº de inscrição (8 dígitos): ");
        if (numero < MENOR_NUMERO || numero > MAIOR_NUMERO) {
            System.out.println("O nº de inscrição precisa ter 8 dígitos.");
            return;
        }
        // A tabela não confere números repetidos (a chave dela é o nome), então o menu confere
        if (posicaoNoVetor(numero) != -1) {
            System.out.println("Já existe um competidor com o nº " + numero + ".");
            return;
        }
        String nome = lerTexto("Nome: ");
        String especie = lerTexto("Espécie: ");
        String mania = lerTexto("Mania: ");
        if (nome.isEmpty() || especie.isEmpty() || mania.isEmpty()) {
            System.out.println("Nome, espécie e mania não podem ficar em branco.");
            return;
        }

        Pet novo = new Pet(numero, nome, especie, mania);
        // inserir: entra na mesa e no vetor; localizar depois: opção 2
        mesa.inserir(novo);
        adicionarAoVetor(novo);
        System.out.println("Inscrição feita: " + novo);
    }

    // Opção 2: mostra todos os competidores com o nome digitado (todas as Lunas, por exemplo)
    private void buscar() {
        String nome = lerTexto("\nNome do competidor: ");
        int achados = mesa.buscarPorNome(nome);
        if (achados > 0) {
            System.out.println("  " + achados + " competidor(es) encontrado(s).");
        }
    }

    // Opção 3: cancela a inscrição pelo número, tirando o pet da mesa e do vetor
    private void cancelar() {
        int numero = lerInteiro("\nNº de inscrição a cancelar: ");
        if (mesa.remover(numero)) {
            removerDoVetor(numero);
            System.out.println("Inscrição " + numero + " cancelada.");
        } else {
            System.out.println("Nenhum competidor com o nº " + numero + ".");
        }
    }

    // Opção 4: mostra a mesa inteira, balde por balde
    private void mostrarMesa() {
        System.out.println("\n♥ Mesa de inscrições (M = " + TAMANHO_MESA + ", função " + FUNCAO_MESA.nome() + ")");
        mesa.exibir();
    }

    // Opção 5: estatísticas da mesa principal e a comparação de M = 13, 17, 53 com as
    // duas funções hash (dados para as perguntas 3 e 4 do relatório)
    private void estatisticas() {
        System.out.println("\n♥ Mesa principal (M = " + TAMANHO_MESA + ", função " + FUNCAO_MESA.nome() + ")");
        mesa.exibirEstatisticas();

        System.out.println("\n♥ Comparação: os mesmos " + total + " competidores em outras mesas");
        FuncaoHash[] funcoes = {new HashSomaCaracteres(), new HashDoGrupo()};
        for (int t = 0; t < TAMANHOS_TESTE.length; t++) {
            for (int f = 0; f < funcoes.length; f++) {
                // Monta uma mesa de teste só para medir; a mesa principal não muda
                TabelaHash teste = new TabelaHash(TAMANHOS_TESTE[t], funcoes[f]);
                for (int i = 0; i < total; i++) {
                    teste.inserir(inscritos[i]);
                }
                System.out.println("\n-- M = " + TAMANHOS_TESTE[t] + " · " + funcoes[f].nome());
                teste.exibirEstatisticas();
            }
        }
    }

    // ---------------------------------------------------------------
    // OPÇÕES 6 e 7: a ordem do desfile (ordenação)
    // ---------------------------------------------------------------

    // Opção 6: ordena todos os inscritos pelo número e mostra a fila do desfile
    private void organizarDesfile() {
        if (total == 0) {
            System.out.println("\nNenhum competidor inscrito.");
            return;
        }
        int a = escolherAlgoritmo();
        if (a == -1) return;

        Resultado r = algoritmos[a].ordenar(vetorDosInscritos());
        System.out.println("\n♥ Ordem do desfile (" + nomesAlgoritmos[a] + ")");
        imprimirFila(algoritmos[a].getOrdenado());
        imprimirResultado(r);
    }

    // Opção 7: ordena poucos competidores (até 10) narrando cada passo
    private void desfilePassoAPasso() {
        if (total < 2) {
            System.out.println("\nSão precisos pelo menos 2 competidores.");
            return;
        }
        int maximo = Math.min(Narrador.LIMITE_PETS, total);
        int quantos = lerInteiro("\nQuantos competidores vão desfilar (2 a " + maximo + ")? ");
        if (quantos < 2 || quantos > maximo) {
            System.out.println("Quantidade inválida.");
            return;
        }
        int a = escolherAlgoritmo();
        if (a == -1) return;

        // Pega os primeiros competidores inscritos, na ordem de inscrição
        Pet[] poucos = new Pet[quantos];
        for (int i = 0; i < quantos; i++) {
            // passo a passo usa os primeiros inscritos, na ordem do arquivo
            poucos[i] = inscritos[i];
        }
        System.out.println("\n♥ Fila antes do desfile:");
        imprimirFila(poucos);
        System.out.println();

        algoritmos[a].setNarracao(true);
        Resultado r = algoritmos[a].ordenar(poucos);
        algoritmos[a].setNarracao(false);   // desliga de novo, para não narrar na corrida

        System.out.println("\n♥ Fila depois do desfile:");
        imprimirFila(algoritmos[a].getOrdenado());
        imprimirResultado(r);
    }

    // ---------------------------------------------------------------
    // OPÇÃO 8: a Grande Corrida
    // ---------------------------------------------------------------

    // Opção 8: roda as 27 medições e imprime as 3 tabelas
    private void grandeCorrida() {
        System.out.println("\nPreparando a corrida... (leva alguns segundos)");
        GrandeCorrida.executar(algoritmos, nomesAlgoritmos);
    }

    // ---------------------------------------------------------------
    // Ajudantes
    // ---------------------------------------------------------------

    // Mostra os três algoritmos e devolve o escolhido (0, 1 ou 2), ou -1 se for inválido
    private int escolherAlgoritmo() {
        System.out.println("\nQual algoritmo vai organizar a fila?");
        for (int i = 0; i < nomesAlgoritmos.length; i++) {
            System.out.println("  " + (i + 1) + " - " + nomesAlgoritmos[i]);
        }
        int escolha = lerInteiro("Algoritmo: ");
        if (escolha < 1 || escolha > nomesAlgoritmos.length) {
            System.out.println("Algoritmo inválido.");
            return -1;
        }
        return escolha - 1;
    }

    // Imprime a fila do desfile, um competidor por linha, com a posição
    private void imprimirFila(Pet[] fila) {
        for (int i = 0; i < fila.length; i++) {
            System.out.println(String.format("  %3dº  %s", i + 1, fila[i]));
        }
    }

    // Imprime os contadores e o tempo de uma ordenação
    private void imprimirResultado(Resultado r) {
        System.out.println(String.format(BRASIL, "\n  comparações: %d | trocas: %d | tempo: %.3f ms",
                r.comparacoes, r.trocas, r.tempoNs / 1_000_000.0));
    }

    // Coloca um pet no fim do vetor, dobrando o vetor se estiver cheio
    private void adicionarAoVetor(Pet pet) {
        if (total == inscritos.length) {
            Pet[] maior = new Pet[inscritos.length * 2];
            for (int i = 0; i < total; i++) {
                maior[i] = inscritos[i];
            }
            inscritos = maior;
        }
        inscritos[total] = pet;
        total++;
    }

    // Tira o pet com esse número do vetor, puxando os seguintes uma casa para a esquerda
    private void removerDoVetor(int numero) {
        int pos = posicaoNoVetor(numero);
        if (pos == -1) return;
        for (int i = pos; i < total - 1; i++) {
            inscritos[i] = inscritos[i + 1];
        }
        inscritos[total - 1] = null;
        total--;
    }

    // Procura o número no vetor (busca sequencial); devolve a posição ou -1
    private int posicaoNoVetor(int numero) {
        for (int i = 0; i < total; i++) {
            if (inscritos[i].getNumero() == numero) return i;
        }
        return -1;
    }

    // Devolve um vetor do tamanho exato com os inscritos (sem as posições vazias do fim)
    private Pet[] vetorDosInscritos() {
        Pet[] v = new Pet[total];
        for (int i = 0; i < total; i++) {
            v[i] = inscritos[i];
        }
        return v;
    }

    // Lê uma linha de texto (sem espaços nas pontas). Se a entrada acabar, encerra o concurso.
    private String lerTexto(String mensagem) {
        System.out.print(mensagem);
        if (!entrada.hasNextLine()) return "0";
        return entrada.nextLine().trim();
    }

    // Lê um número inteiro; repete a pergunta até a pessoa digitar um número válido.
    // Sempre nextLine + parseInt: evita o problema do nextInt deixar o Enter para trás.
    private int lerInteiro(String mensagem) {
        while (true) {
            String texto = lerTexto(mensagem);
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número.");
            }
        }
    }
}
