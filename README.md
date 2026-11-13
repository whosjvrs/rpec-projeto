# Pet Hash & Sort — Concurso de Fofura 🐾

Programa de console em Java que junta **tabela hash**, **algoritmos de ordenação** e uma **comparação de desempenho**, tudo contado como um concurso de fofura de bichinhos.

Projeto final de **Resolução de Problemas Estruturados em Computação** (Turma A · Manhã · Profa. Lisiane Reips).

**Stack:** Java 17+ · projeto simples (sem Maven) · IntelliJ IDEA

---

## Equipe e frentes

| Frente | O que faz | Peso | Responsável | Revisor(a) | Branch | Pasta |
|---|---|---|---|---|---|---|
| 1 · Tabela hash | Lista encadeada, tabela, 2 funções hash, estatísticas, testes com M = 13, 17 e 53 | 30% | _a definir_ | _a definir_ | `feat/hash` | `src/hash/` |
| 2 · Ordenação | Bubble, Insertion e Quick Sort, contadores, modo passo a passo | 30% | _a definir_ | _a definir_ | `feat/ordenacao` | `src/ordenacao/` |
| 3 · Dados e Grande Corrida | Leitura do `pets.txt`, gerador de 1.000 / 10.000 pets, 27 medições | 15% | _a definir_ | _a definir_ | `feat/dados` | `src/dados/` e `src/corrida/` |
| 4 · Integração e menu | `Main`, `Menu`, README, montagem do relatório e da apresentação | 10% + 15% | _a definir_ | _a definir_ | `feat/menu` | `src/menu/` e `src/Main.java` |

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
├── relatorio/                   ← tabelas exportadas e PDF final
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
git switch feat/SUA-FRENTE        # feat/hash, feat/ordenacao, feat/dados ou feat/menu
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

// ordenacao/Resultado.java
long comparacoes; long trocas; long tempoNs;

// dados/GeradorPets.java
Pet[] gerar(int n, Situacao situacao);    // ALEATORIO, ORDENADO ou INVERSO
```

---

## Decisões técnicas

| Decisão | Escolha |
|---|---|
| Cenário | **C · Concurso de Fofura**: a mesa de inscrições é a tabela hash, e a ordem do desfile é a ordenação por nº de inscrição |
| Tamanhos da tabela | M = **13**, **17** e **53** |
| Função hash 1 | Soma dos códigos dos caracteres `% M` |
| Função hash 2 | Polinomial, base 31: `h = (h * 31 + letra) % M` |
| Chave | Nome em maiúsculas e sem acento (`Normalizer`, forma NFD) |
| Colisão | **Nomes diferentes no mesmo balde.** Lunas no mesmo balde não contam |
| Remover | Por nº de inscrição, percorrendo todos os baldes |
| Pivô do Quick Sort | **Último elemento** (expõe o pior caso da pergunta 9) |
| Insertion Sort | Conta **deslocamentos** como trocas |
| Medição | 1 rodada de aquecimento + média de 3 rodadas, com `System.nanoTime()` |
| Narração | Só com até 10 pets; desligada na Grande Corrida |

🚫 **Proibido pelo enunciado:** `HashMap`, `Hashtable`, `HashSet`, `LinkedList` ou qualquer estrutura pronta para a tabela; `Arrays.sort`, `Collections.sort` ou qualquer ordenação pronta.

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
| 4 · Integração | — | Revisa e junta todas as respostas no PDF |

---

## Entregáveis (data: _confirmar com a professora_)

- **Código-fonte** (`.java`) organizado e comentado, com o `dados/pets.txt`.
- **Relatório** (PDF, de 3 a 6 páginas): cenário, decisões do grupo, tabelas da Grande Corrida e respostas das 9 perguntas.
- **Apresentação** de 5 a 8 minutos, com o programa funcionando ao vivo.
- **Bônus:** ordenar ao vivo com pelúcias ou fotos numeradas, narrando um algoritmo passo a passo.
