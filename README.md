# Pet Hash & Sort — Concurso de Fofura 🐾

Programa de console em Java que junta **tabela hash**, **algoritmos de ordenação** e uma **comparação de desempenho**, contado como um concurso de fofura de bichinhos: as inscrições ficam numa tabela hash (chave = nome do pet) e o desfile acontece em ordem de número de inscrição.

**Disciplina:** Resolução de Problemas Estruturados em Computação · Turma A · Manhã · Profa. Lisiane Reips

**Grupo:** Bianca França, Geovana Vincensi, João Victor dos Reis, Luiz Eduardo Dias e Tabatha Aita

**Relatório:** `relatorio/Relatorio_Pet_Hash_Sort.pdf`

---

## Como executar

Requer **Java 17** ou mais novo. Na pasta do projeto (para o programa encontrar `dados/pets.txt`):

```bash
javac -d out $(find src -name "*.java")
java -cp out Main
```

No IntelliJ: abrir a pasta do projeto, marcar `src` como *Sources Root* e rodar o `Main.java` com o *Working directory* na pasta do projeto.

Testes que rodam sem o menu: `java -cp out dados.TesteDados` e `java -cp out ordenacao.TesteOrdenacao`.

## Menu

```
1 - Inscrever competidor
2 - Buscar competidores por nome
3 - Cancelar inscrição (nº de inscrição)
4 - Mostrar mesa de inscrições
5 - Estatísticas da mesa          (M = 13, 17 e 53 com as duas funções hash)
6 - Organizar o desfile           (Bubble, Insertion ou Quick Sort)
7 - Desfile passo a passo         (narração com até 10 pets)
8 - A Grande Corrida              (27 medições; salva relatorio/grande-corrida.csv)
0 - Encerrar o concurso
```

## Estrutura

```
dados/pets.txt                 50 competidores (numero;nome;especie;mania, UTF-8)
relatorio/                     relatório em PDF
src/
├── Main.java                  lê o arquivo e abre o menu
├── modelo/Pet.java            ficha do competidor e normalização da chave
├── hash/                      tabela hash, lista ligada (No) e as duas funções hash
├── ordenacao/                 Bubble, Insertion e Quick Sort, contadores e narração
├── dados/                     leitura do arquivo e gerador de 1.000 / 10.000 pets
├── corrida/GrandeCorrida.java as 27 medições e as 3 tabelas
└── menu/Menu.java             as 9 opções do menu
```

## Principais decisões

| Decisão | Escolha |
|---|---|
| Chave da tabela | Nome em maiúsculas e sem acento (Paçoca → PACOCA) |
| Colisões | Encadeamento com lista ligada própria (sem estruturas prontas do Java) |
| Funções hash | Soma dos códigos dos caracteres `% M` e polinomial de base 31 |
| Mesa principal | M = 53 com a base 31 |
| Colisão (estatísticas) | Nomes **diferentes** no mesmo balde |
| Pivô do Quick Sort | Último elemento do trecho |
| Bubble Sort | Com parada antecipada |
| Insertion Sort | Trocas contadas como deslocamentos |
| Medição | `System.nanoTime()`, 1 rodada de aquecimento + média de 3 |

Nenhuma estrutura pronta (`HashMap`, `Hashtable`, `HashSet`, `LinkedList`) nem ordenação pronta (`Arrays.sort`, `Collections.sort`) foi usada.
