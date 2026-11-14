# Pet Hash & Sort — Concurso de Fofura 🐾

Programa de console em Java que junta **tabela hash**, **algoritmos de ordenação** e uma **comparação de desempenho**, tudo contado como um concurso de fofura de bichinhos.

Projeto final de **Resolução de Problemas Estruturados em Computação** (Turma A · Manhã · Profa. Lisiane Reips).

**Stack:** Java 17+ · projeto simples (sem Maven) · IntelliJ IDEA

---

## Equipe e frentes

| Frente | O que faz | Peso | Responsável | Revisor(a) | Branch | Pasta |
|---|---|---|---|---|---|---|
| 1 · Tabela hash | Lista encadeada, tabela, 2 funções hash, estatísticas, testes com M = 13, 17 e 53 | 30% | Tabatha | _a definir_ | `feat/hash` | `src/hash/` |
| 2 · Ordenação | Bubble, Insertion e Quick Sort, contadores, modo passo a passo | 30% | Bianca | _a definir_ | `feat/ordenacao` | `src/ordenacao/` |
| 3 · Dados e Grande Corrida | Leitura do `pets.txt`, gerador de 1.000 / 10.000 pets, 27 medições | 15% | João | _a definir_ | `feat/dados` | `src/dados/` e `src/corrida/` |
| 4 · Menu e integração | `Main` e `Menu`, ligando a tabela, a ordenação e a corrida | 10% | _a definir_ | _a definir_ | `feat/menu` | `src/menu/` e `src/Main.java` |
| 5 · Relatório e apresentação | Respostas das 9 perguntas no PDF, roteiro da apresentação, bônus com pelúcias, revisão geral do código | 15% | _a definir_ | — | `feat/relatorio` | `relatorio/` |

> ⚠️ **Defesa de autoria:** a professora sorteia **qualquer integrante** para alterar **qualquer parte** do código. Se dois sorteados recusarem, **o grupo todo fica com zero**. Por isso, revise e estude as outras frentes, não só a sua.

---

## Como o repositório funciona

- A `main` já vem com a **base comum**: a estrutura de pastas, a classe `Pet`, as interfaces combinadas e os 50 pets em `dados/pets.txt`.
- Cada frente tem **sua branch** e **sua pasta**. Cada pessoa mexe só na pasta da sua frente.
- Quando a parte estiver funcionando, ela volta para a `main` por **Pull Request**, revisado pelo revisor da frente.

```
rpec-projeto/
├── README.md
├── .gitignore
├── dados/
│   └── pets.txt                 ← numero;nome;especie;mania (UTF-8, 50 pets)
├── relatorio/                   ← frente 5: tabelas exportadas e PDF final
└── src/
    ├── Main.java                ← só chama o Menu
    ├── modelo/
    │   └── Pet.java             ← BASE COMUM, não alterar sem avisar
    ├── hash/                    ← frente 1
    │   ├── FuncaoHash.java      ← BASE COMUM (interface)
    │   ├── No.java
    │   ├── TabelaHash.java
    │   ├── HashSomaCaracteres.java
    │   └── HashDoGrupo.java
    ├── ordenacao/               ← frente 2
    │   ├── Ordenador.java       ← BASE COMUM (classe base)
    │   ├── Resultado.java       ← BASE COMUM
    │   ├── BubbleSort.java
    │   ├── InsertionSort.java
    │   ├── QuickSort.java
    │   └── Narrador.java
    ├── dados/                   ← frente 3
    │   ├── Situacao.java        ← BASE COMUM (enum)
    │   ├── LeitorArquivo.java
    │   └── GeradorPets.java
    ├── corrida/                 ← frente 3
    │   └── GrandeCorrida.java
    └── menu/                    ← frente 4
        └── Menu.java
```

---

## Primeiros passos (cada integrante)

### 1. Instalar o necessário

- **Java 17 ou mais novo.** Para conferir, rode `java -version`.
- **Git.** Para conferir, rode `git --version`.
- **IntelliJ IDEA** (a Community é suficiente).
- Peça para o João te adicionar como colaborador(a) do repositório e aceite o convite que chega por email.

### 2. Configurar o Git (só na primeira vez)

```bash
git config --global user.name "Seu Nome"
git config --global user.email "email-da-sua-conta-github"
```

**Login no terminal:** a senha do GitHub **não funciona** no `git push`. Use uma das opções abaixo.

- Com o GitHub CLI: rode `gh auth login` e escolha *GitHub.com → HTTPS → Login with a web browser*.
- Ou com um token: em github.com/settings/tokens → *Generate new token (classic)*, marque **repo** e copie o token (`ghp_...`). No `git push`, use o seu usuário e cole o token no lugar da senha. Para não precisar colar toda vez:
  ```bash
  git config --global credential.helper store
  ```

### 3. Clonar e entrar na sua branch

As branches **já existem**, então não é para criar outra. Só entre na sua:

```bash
git clone https://github.com/whosjvrs/rpec-projeto.git
cd rpec-projeto
git switch feat/SUA-FRENTE        # feat/hash, feat/ordenacao, feat/dados, feat/menu ou feat/relatorio
```

> 💡 Se o caminho da pasta tiver espaços, use aspas no `cd`: `cd "/home/voce/minha pasta"`.

### 4. Abrir no IntelliJ

1. `File → Open` → selecione a pasta `rpec-projeto`.
2. Se o código aparecer sem cor ou com erros em tudo, clique com o botão direito na pasta `src` → `Mark Directory as → Sources Root`.
3. `File → Project Structure → Project` → em **SDK**, escolha o Java 17+.
4. Em `Run → Edit Configurations`, deixe o **Working directory** como a pasta `rpec-projeto`. Sem isso, o programa não acha o `dados/pets.txt`.

### 5. Testar se está tudo certo

```bash
javac -d out $(find src -name "*.java") && java -cp out Main
```

Tem que aparecer `Pet Hash & Sort - Concurso de Fofura`.

---

## Contratos combinados (base comum)

Todas as frentes programam em cima destas assinaturas. **Não mude nenhuma delas sem avisar o grupo.**

```java
// modelo/Pet.java
int getNumero();        // nº de inscrição, 8 dígitos, único
String getNome();       // "Paçoca"
String getEspecie();
String getMania();
String getChave();      // nome em maiúsculas e sem acento: "PACOCA"

// hash/FuncaoHash.java
int calcular(String chave, int m);        // devolve um índice de 0 a m-1

// ordenacao/Ordenador.java
Resultado ordenar(Pet[] vetor);           // ordena uma CÓPIA e devolve os contadores
void setNarracao(boolean ligada);         // passo a passo, só com até 10 pets
Pet[] copiar(Pet[] original);             // ajudante: cópia do vetor
boolean maior(Pet a, Pet b);              // ajudante: compara por número e CONTA a comparação
void trocar(Pet[] v, int a, int b);       // ajudante: troca dois pets e CONTA a troca

// ordenacao/Resultado.java
long comparacoes; long trocas; long tempoNs;

// dados/GeradorPets.java
Pet[] gerar(int n, Situacao situacao);    // ALEATORIO, ORDENADO ou INVERSO
```

---

## O que cada frente entrega (checklist)

Marque no PR o que já está pronto.

### Frente 1 · Tabela hash (`src/hash/`)
- [x] `No.java`: guarda um `Pet` e o próximo `No` (lista ligada própria, **sem** `LinkedList`).
- [x] `HashSomaCaracteres.java`: implementa `FuncaoHash` somando os códigos dos caracteres `% m`.
- [x] `HashDoGrupo.java`: implementa `FuncaoHash` com a polinomial base 31.
- [x] `TabelaHash.java`, com o construtor `TabelaHash(int m, FuncaoHash funcao)` e os métodos:
  - [x] `void inserir(Pet pet)`
  - [x] `int buscarPorNome(String nome)`: mostra **todos** os pets com aquele nome e devolve quantos achou
  - [x] `boolean remover(int numero)`: percorre todos os baldes
  - [x] `void exibir()`: mostra a tabela balde por balde
  - [x] `void exibirEstatisticas()`: fator de carga, baldes vazios, maior lista e colisões (só nomes diferentes no mesmo balde)
- [ ] Imprimir as estatísticas para M = 13, 17 e 53, com as duas funções lado a lado (perguntas 3 e 4): fica na opção 5 do menu (frente 4).

### Frente 2 · Ordenação (`src/ordenacao/`)
- [ ] `BubbleSort`, `InsertionSort` e `QuickSort` estendem `Ordenador`.
- [ ] Cada um ordena uma **cópia** do vetor por `getNumero()` e devolve um `Resultado` com as comparações, as trocas (no Insertion, os deslocamentos) e o tempo.
- [ ] O Quick Sort usa o **último elemento** como pivô.
- [ ] `Narrador.java`: as frases do cenário. Quando `narracao` estiver ligada, cada comparação e cada troca é narrada, e no fim de cada passagem aparecem os contadores:
  ```
  ♥ Bubble Sort · passagem 1
    Luna (60218841) > Thor (31057720)? SIM → trocam de lugar na fila do desfile!
    Luna (60218841) > Zeus (70334512)? NÃO → Luna segue desfilando no lugar.
    fim da passagem 1 → comparações: 2 | trocas: 1
  ```

### Frente 3 · Dados e Grande Corrida (`src/dados/` e `src/corrida/`)
- [ ] `LeitorArquivo.java`: `Pet[] ler(String caminho)` lê o `dados/pets.txt` em **UTF-8**.
- [ ] `GeradorPets.java`: `Pet[] gerar(int n, Situacao situacao)` gera números **únicos** de 8 dígitos (sem `HashSet`), combinando nomes, espécies e manias.
- [ ] `GrandeCorrida.java`: roda as **27 medições** (3 algoritmos × 3 situações × 50 / 1.000 / 10.000), com 1 rodada de aquecimento + média de 3, e imprime as **3 tabelas** (tempo, comparações e trocas). Se der tempo, exporta também um `.csv` para `relatorio/`.

### Frente 4 · Menu e integração (`src/menu/` e `src/Main.java`)
- [ ] `Menu.java` com as 9 opções e os textos do cenário:
  ```
  1 - Inscrever competidor
  2 - Buscar competidores por nome
  3 - Cancelar inscrição (nº de inscrição)
  4 - Mostrar mesa de inscrições
  5 - Estatísticas da mesa
  6 - Organizar o desfile
  7 - Desfile passo a passo
  8 - A Grande Corrida
  0 - Encerrar o concurso
  ```
- [ ] `Main.java` lê os pets, monta a tabela e chama o `Menu`.

### Frente 5 · Relatório e apresentação (`relatorio/`)
- [ ] Juntar no PDF (3 a 6 páginas) o cenário, as decisões do grupo, as tabelas da Grande Corrida e as respostas das 9 perguntas escritas por cada frente.
- [ ] Conferir se as respostas usam os **números medidos** pelo grupo, e não só teoria.
- [ ] Montar o roteiro da apresentação (5 a 8 minutos, com o programa rodando ao vivo).
- [ ] Preparar o bônus: pelúcias ou fotos numeradas para ordenar ao vivo, narrando um algoritmo.
- [ ] Revisar os PRs das outras frentes. Como quase não programa, essa pessoa precisa estudar bem o código de todos para a defesa.

---

## Decisões técnicas

| Decisão | Escolha |
|---|---|
| Cenário | **C · Concurso de Fofura**: a mesa de inscrições é a tabela hash, e a ordem do desfile é a ordenação por nº de inscrição |
| Tamanhos da tabela | M = **13**, **17** e **53** |
| Função hash 1 | Soma dos códigos dos caracteres `% M` |
| Função hash 2 | Polinomial, base 31: `h = h * 31 + letra` e, no fim, `Math.floorMod(h, M)` |
| Chave | Nome em maiúsculas e sem acento (`Normalizer`, forma NFD) |
| Colisão | **Nomes diferentes no mesmo balde.** Lunas no mesmo balde não contam |
| Remover | Por nº de inscrição, percorrendo todos os baldes |
| Pivô do Quick Sort | **Último elemento** (expõe o pior caso da pergunta 9) |
| Bubble Sort | **Com parada antecipada**: se uma passagem não faz nenhuma troca, para (melhor caso O(n), como nos slides) |
| Contadores | Nos ajudantes do `Ordenador`: todo `maior` conta uma comparação, todo `trocar` conta uma troca |
| Insertion Sort | Conta **deslocamentos** como trocas |
| Medição | 1 rodada de aquecimento + média de 3 rodadas, com `System.nanoTime()` |
| Narração | Só com até 10 pets; desligada na Grande Corrida |
| Tabela principal do menu | **M = 53 com a base 31** (menos colisões e menor lista nos testes com os 50 pets) |

🚫 **Proibido pelo enunciado:** `HashMap`, `Hashtable`, `HashSet`, `LinkedList` ou qualquer estrutura pronta para a tabela; `Arrays.sort`, `Collections.sort` ou qualquer ordenação pronta.

---

## Padrões de código

- **Nomes em português**, sem acento nos identificadores (`calcularIndice`, não `calcularÍndice`).
- Classes em `PascalCase` (`TabelaHash`), métodos e variáveis em `camelCase` (`fatorCarga`), constantes em `MAIUSCULAS` (`TAMANHO_PADRAO`).
- **Toda classe começa com um comentário** dizendo o que ela faz. **Todo método tem um comentário** de uma linha explicando o que faz. A professora avalia "organizado e comentado", e isso também ajuda na defesa.
- Comentários em português.
- Indentação de 4 espaços. No IntelliJ, `Ctrl + Alt + L` formata o arquivo.
- Arquivos sempre lidos e salvos em **UTF-8**.
- Nada de código que ninguém do grupo saiba explicar. Se usar algo diferente, comente o porquê.

Exemplo:

```java
package hash;

/**
 * Função hash que soma os códigos dos caracteres da chave.
 * Fraqueza: anagramas (AMOR e ROMA) sempre caem no mesmo balde.
 */
public class HashSomaCaracteres implements FuncaoHash {

    // Soma o código de cada letra e devolve o resto da divisão por m
    @Override
    public int calcular(String chave, int m) {
        int soma = 0;
        for (int i = 0; i < chave.length(); i++) {
            soma += chave.charAt(i);
        }
        return soma % m;
    }
}
```

---

## Ordem de trabalho

1. ✅ **Base comum na `main`:** estrutura, `Pet`, interfaces e 50 pets. **Já está pronta.**
2. **Em paralelo, desde já:**
   - frente 1 → tabela hash;
   - frente 2 → os três algoritmos;
   - frente 3 → `LeitorArquivo` e `GeradorPets`;
   - frente 4 → `Menu` com as opções e os textos (pode chamar métodos vazios por enquanto);
   - frente 5 → estrutura do relatório e roteiro da apresentação.
3. **Assim que a frente 2 entrar na `main`:** a frente 3 faz a `GrandeCorrida` usando os ordenadores.
4. **Integração:** a frente 4 liga o menu à tabela e aos algoritmos.
5. **Medições e relatório:** rodar as 27 medições, preencher as tabelas e responder às 9 perguntas.
6. **Ensaio da apresentação**, mais uma rodada de "sorteio simulado": cada pessoa altera uma parte que **não** é dela.

### Testando a sua parte sozinho(a)

Antes do menu ficar pronto, crie na sua pasta uma classe de teste com `main`, por exemplo `src/hash/TesteHash.java`:

```java
package hash;

import modelo.Pet;

// Teste rápido da frente 1, sem depender do menu
public class TesteHash {
    public static void main(String[] args) {
        TabelaHash tabela = new TabelaHash(13, new HashSomaCaracteres());
        tabela.inserir(new Pet(60218841, "Luna", "gata", "dorme em cima do teclado"));
        tabela.inserir(new Pet(31057720, "Thor", "cão", "late para o aspirador"));
        tabela.exibir();
    }
}
```

Para rodar: `javac -d out $(find src -name "*.java") && java -cp out hash.TesteHash`. Pode deixar a classe no repositório, porque ela ajuda a mostrar a sua parte funcionando.

---

## Fluxo de trabalho

### 1. Trabalhe na sua branch

```bash
git switch feat/SUA-FRENTE
git pull origin main             # sempre antes de começar: traz o que já foi integrado
```

### 2. Commits pequenos e com mensagem clara

| Tipo | Quando usar | Exemplo |
|---|---|---|
| `feat` | funcionalidade nova | `feat(hash): inserir e buscar por nome` |
| `fix` | correção de erro | `fix(ordenacao): contador de trocas do quick` |
| `refactor` | melhoria sem mudar o comportamento | `refactor(hash): extrai método de estatísticas` |
| `dados` | pets, arquivo de dados | `dados: adiciona pets da Bianca` |
| `docs` | README, relatório, tabelas | `docs: tabelas da Grande Corrida` |

```bash
git add src/hash/
git commit -m "feat(hash): inserir e buscar por nome"
git push
```

### 3. Compile antes de cada push

```bash
javac -d out $(find src -name "*.java") && java -cp out Main
```

> 💡 Se o Quick Sort der `StackOverflowError` com 10.000 pets, rode com mais pilha: `java -Xss64m -cp out Main`. Registrem isso no relatório (pergunta 9).

### 4. Juntar na main (Pull Request)

Quando a sua parte estiver funcionando:

```bash
git switch main && git pull               # pega o que já foi integrado
git switch feat/SUA-FRENTE
git merge main                            # traz a main para a sua branch
javac -d out $(find src -name "*.java")   # confere se tudo ainda compila
git push
```

Depois, no GitHub: **Pull requests → New pull request** → `base: main` ← `compare: feat/SUA-FRENTE`.
O **revisor da frente** lê o código, tira as dúvidas e aprova. Só depois disso alguém faz o merge.

> 💡 A revisão também é estudo para a defesa: quem revisa precisa entender o código a ponto de conseguir alterá-lo.

---

## Regras de convivência

- ✅ Mexa **apenas na pasta da sua frente**.
- 🚫 **Não altere** a base comum (`Pet`, `FuncaoHash`, `Ordenador`, `Resultado`, `Situacao`) sem avisar o grupo.
- 🚫 Nunca faça commit direto na `main`. Tudo entra por Pull Request.
- ⚠️ O `dados/pets.txt` é o único arquivo que todo mundo pode editar. Faça `git pull` **logo antes** de mexer nele.
- 🚫 Não suba `out/`, `.idea/`, `*.class` nem `*.iml`. O `.gitignore` já cuida disso.
- ✅ Só abra PR com o código **compilando**.

---

## Perguntas do relatório por frente

| Frente | Perguntas | O programa precisa mostrar |
|---|---|---|
| 1 · Hash | 1 a 5 | Busca por nome, tabela balde por balde, estatísticas para M = 13/17/53 e para as duas funções, fator de carga com 10.000 pets |
| 2 · Ordenação | 6 e 8 | Comparações do Insertion com os dados já ordenados |
| 3 · Corrida | 7 e 9 | As 3 tabelas (tempo, comparações e trocas) e o comportamento do Quick nos casos ordenado e inverso |
| 4 · Menu | — | — |
| 5 · Relatório | — | Revisa e junta todas as respostas no PDF |

---

## Entregáveis (data: _confirmar com a professora_)

- **Código-fonte** (`.java`) organizado e comentado, com o `dados/pets.txt`.
- **Relatório** (PDF, de 3 a 6 páginas): cenário, decisões do grupo, tabelas da Grande Corrida e respostas das 9 perguntas.
- **Apresentação** de 5 a 8 minutos, com o programa funcionando ao vivo.
- **Bônus:** ordenar ao vivo com pelúcias ou fotos numeradas, narrando um algoritmo passo a passo.
