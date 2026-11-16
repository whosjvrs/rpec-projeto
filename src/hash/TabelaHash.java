package hash;

import java.text.Normalizer;
import modelo.Pet;

/**
 * Tabela hash com encadeamento: a "mesa de inscrições" do Concurso de Fofura.
 * A chave é o nome do pet em maiúsculas e sem acento (getChave()).
 * Cada posição do vetor (balde) guarda uma lista ligada de nós.
 * Baseada no TabelaHashSimples do material de apoio da professora.
 */
public class TabelaHash {

    private No[] tabela;        // vetor de listas (os baldes)
    private int tamanho;        // quantidade de baldes (M)
    private int quantidade;     // quantos pets estão na tabela
    private FuncaoHash funcao;  // função hash usada (soma ou base 31)

    // Cria a tabela com "tamanho" baldes vazios e a função hash escolhida
    public TabelaHash(int tamanho, FuncaoHash funcao) {
        this.tamanho = tamanho;
        this.funcao = funcao;
        this.tabela = new No[tamanho];  // todos os baldes começam vazios (null)
        this.quantidade = 0;
    }

    // Transforma um nome digitado na chave: maiúsculas e sem acento ("Paçoca" -> "PACOCA")
    private static String normalizar(String nome) {
        String semAcento = Normalizer.normalize(nome.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return semAcento.toUpperCase();
    }

    // Calcula em qual balde fica uma chave, usando a função hash escolhida
    private int indice(String chave) {
        return funcao.calcular(chave, tamanho);
    }

    // INSERIR: calcula o balde e coloca o pet no início da lista dele
    public void inserir(Pet pet) {
        int i = indice(pet.getChave());
        No novo = new No(pet);
        novo.proximo = tabela[i];   // o novo aponta para quem já estava lá
        tabela[i] = novo;           // e passa a ser o primeiro da lista
        quantidade++;
    }

    // BUSCAR: vai direto ao balde do nome e mostra TODOS os pets com esse nome
    public int buscarPorNome(String nome) {
        String chave = normalizar(nome);
        int i = indice(chave);
        int encontrados = 0;

        No atual = tabela[i];
        while (atual != null) {
            // o balde pode ter outros nomes (colisão), então confere a chave
            if (atual.pet.getChave().equals(chave)) {
                System.out.println("  " + atual.pet);
                encontrados++;
            }
            atual = atual.proximo;
        }

        if (encontrados == 0) {
            System.out.println("  Nenhum competidor chamado " + nome + ".");
        }
        return encontrados;
    }

    // EXIBIR: mostra cada balde e a lista de pets que está nele
    public void exibir() {
        for (int i = 0; i < tamanho; i++) {
            System.out.print("[" + i + "] ");
            No atual = tabela[i];
            while (atual != null) {
                System.out.print(atual.pet.getNome() + " (" + atual.pet.getNumero() + ") -> ");
                atual = atual.proximo;
            }
            System.out.println("null");
        }
    }


    // REMOVER: procura o número em todos os baldes e tira o nó da lista
    // (percorre tudo porque a chave da tabela é o nome, não o número)
    public boolean remover(int numero) {
        for (int i = 0; i < tamanho; i++) {
            No anterior = null;
            No atual = tabela[i];
            while (atual != null) {
                if (atual.pet.getNumero() == numero) {
                    if (anterior == null) tabela[i] = atual.proximo;   // era o primeiro
                    else anterior.proximo = atual.proximo;            // estava no meio/fim
                    quantidade--;
                    return true;
                }
                anterior = atual;
                atual = atual.proximo;
            }
        }
        return false;
    }

    // ESTATÍSTICAS: percorre os baldes uma vez só e calcula tudo junto
    // Colisão = nomes DIFERENTES no mesmo balde (várias Lunas não contam)
    public void exibirEstatisticas() {
        int vazios = 0, maiorLista = 0, colisoes = 0;

        for (int i = 0; i < tamanho; i++) {
            if (tabela[i] == null) {
                vazios++;
                continue;
            }
            int tamanhoLista = 0, nomesDiferentes = 0;
            No atual = tabela[i];
            while (atual != null) {
                tamanhoLista++;
                // procura o primeiro nó do balde com o mesmo nome do atual
                No anterior = tabela[i];
                while (!anterior.pet.getChave().equals(atual.pet.getChave())) {
                    anterior = anterior.proximo;
                }
                if (anterior == atual) nomesDiferentes++;   // nome novo neste balde
                atual = atual.proximo;
            }
            if (tamanhoLista > maiorLista) maiorLista = tamanhoLista;
            colisoes += nomesDiferentes - 1;
        }

        System.out.println("  Baldes (M) ........ " + tamanho);
        System.out.println("  Pets inscritos .... " + quantidade);
        System.out.printf("  Fator de carga .... %.2f%n", (double) quantidade / tamanho);
        System.out.println("  Baldes vazios ..... " + vazios);
        System.out.println("  Maior lista ....... " + maiorLista);
        System.out.println("  Colisões .......... " + colisoes);
    }
}