# Testes automatizados do Papyrus: Cadastro de Matéria

Este pacote contém os testes automatizados do módulo **Cadastro de Matéria** do sistema Papyrus, escritos com **Robot Framework** e **SeleniumLibrary**.

A proposta original era reunir todos os testes do grupo em apenas dois arquivos e executá-los juntos. Optamos por dividir o trabalho: cada integrante automatizou os seus 5 casos de teste do plano de teste e entrega seus próprios arquivos. Este documento descreve os 5 casos deste pacote e explica como os arquivos foram estruturados.

## Arquivos

```
papyrus_testes/
├── papyrus_resources.robot    # variáveis, localizadores e keywords
└── papyrus_testes.robot       # casos de teste CT1 a CT5
```

A estrutura segue o padrão de separar **o que é reutilizável** (resources) de **o que é testado** (testes). Assim, os casos de teste ficam legíveis, escritos com os mesmos passos do plano de teste, e qualquer mudança na tela do sistema é ajustada em um único lugar.

## Perfil de teste

Foi criado no Papyrus um perfil exclusivo para a execução dos testes automatizados:

| Campo  | Valor             |
|--------|-------------------|
| E-mail | `teste@teste.com` |
| Senha  | `123456`          |

Essas credenciais ficam nas variáveis `${EMAIL}` e `${SENHA}` do `papyrus_resources.robot`. O perfil separado evita que as matérias criadas pelos testes se misturem com dados de outros usuários.

## `papyrus_resources.robot`

Este arquivo é importado pelo arquivo de testes e tem três seções.

### Settings

Importa as bibliotecas usadas:

- **SeleniumLibrary**: controla o navegador (abrir páginas, clicar, digitar, verificar elementos).
- **String**: usada para medir o tamanho do texto digitado no campo Nome.

### Variables

Reúne a configuração do ambiente, as credenciais e os localizadores dos elementos da tela. Os localizadores foram definidos a partir do código-fonte do frontend do Papyrus, usando os `id` dos campos sempre que existem.

| Variável | Valor | O que representa |
|----------|-------|------------------|
| `${BROWSER}` | `Chrome` | Navegador usado |
| `${URL}` | `http://localhost:5173` | Endereço do sistema |
| `${EMAIL}` / `${SENHA}` | perfil de teste | Credenciais de login |
| `${CAMPO_EMAIL}` | `id:email` | Campo de e-mail do login |
| `${CAMPO_SENHA}` | `id:senha` | Campo de senha do login |
| `${BOTAO_ENTRAR}` | botão `submit` do formulário | Botão "Entrar" |
| `${BOTAO_NOVA}` | botão com texto "Nova matéria" | Abre o formulário de cadastro |
| `${MODAL_MATERIA}` | `div[@role='dialog']` | Janela do formulário de matéria |
| `${CAMPO_NOME}` | `id:nome` | Campo Nome da matéria |
| `${CAMPO_DESCRICAO}` | `id:descricao` | Campo Descrição da matéria |
| `${BOTAO_CRIAR}` | botão `submit` do modal | Botão "Criar matéria" |
| `${CARDS_MATERIAS}` | `//article` | Cartões da listagem de matérias |
| `${MSG_SUCESSO}` | `Matéria criada com sucesso.` | Mensagem exibida pelo sistema após o cadastro |
| `${QTD_MATERIAS}` / `${NOME_LONGO}` | valores iniciais | Preenchidas durante a execução dos testes |

### Keywords

As keywords transformam ações técnicas do Selenium em passos escritos em português, no mesmo vocabulário do plano de teste. Várias delas recebem argumentos embutidos no nome, como `Digitar no campo nome "AB"`.

**Navegador**

- `Abrir o navegador`: abre o Chrome no endereço do sistema. Também desativa o gerenciador de senhas do Chrome, que exibia um aviso de troca de senha (a senha `123456` aparece em listas de senhas vazadas) e atrapalhava a execução.
- `Atualizar a página`: recarrega a página e aguarda a listagem carregar.
- `Fechar o navegador`: tira uma captura de tela como evidência e fecha o navegador.

**Login e navegação**

- `Acessar a pagina de login`, `Digitar no campo email "..."`, `Digitar no campo senha "..."`, `Acionar o botão entrar`: passos do login.
- `Verificar que o login foi realizado`: confirma o redirecionamento para o dashboard.
- `Acessar a pagina de matérias`: abre a tela de Matérias e aguarda a listagem carregar.
- `Efetuar login e acessar Matérias`: agrupa todos os passos acima e corresponde ao primeiro passo de todos os casos do plano de teste.

**Cadastro de matéria**

- `Acionar o botão Nova matéria`: abre o formulário de cadastro.
- `Digitar no campo nome "..."`: preenche o nome com o texto informado.
- `Digitar no campo nome "101" letras "A"`: gera e digita um nome com a quantidade de caracteres informada.
- `Digitar no campo descrição "..."`: preenche a descrição.
- `Acionar o botão Criar matéria`: envia o formulário.
- `Acionar Criar matéria se o campo nome aceitar mais de "100" caracteres`: só envia o formulário se o campo tiver aceitado o texto inteiro, como pede o passo 6 do CT1.
- `Guardar a quantidade de matérias cadastradas`: registra quantas matérias existem antes do cadastro.

**Verificações**

- `Verificar que a mensagem "..." é exibida` e `Verificar que a mensagem de sucesso é exibida`: conferem os textos exibidos pelo sistema.
- `Verificar que o cadastro foi rejeitado`: confere que o formulário continua aberto para correção e que nenhuma mensagem de sucesso apareceu. Se o formulário tiver fechado, o teste falha com a mensagem "O sistema aceitou o cadastro".
- `Verificar que o campo nome é obrigatório`: confere o atributo `required` do campo Nome.
- `Verificar que a matéria "..." aparece na listagem` / `não aparece na listagem`: procuram a matéria pelo nome nos cartões da listagem.
- `Verificar que a matéria "..." aparece sem descrição`: confere que o cartão indica "Sem descrição cadastrada.".
- `Verificar que a descrição "..." foi mantida`: confere que o texto da descrição foi preservado no formulário.
- `Verificar que nenhuma nova matéria foi criada`: compara a quantidade de matérias com a registrada antes do cadastro.

## `papyrus_testes.robot`

Este arquivo contém apenas os casos de teste. Na seção **Settings**, ele importa o `papyrus_resources.robot` e define:

- **Test Setup** `Abrir o navegador`: cada teste começa com um navegador novo.
- **Test Teardown** `Fechar o navegador`: cada teste termina com captura de tela e fechamento do navegador, mesmo quando falha.

Assim, os testes são independentes entre si. Cada caso tem `[Documentation]` com o objetivo e `[Tags]` para classificação (`materia`, `limite`, `sucesso`, `validacao`, `obrigatorio`).

| Caso | Cenário | O que o teste verifica | Resultado |
|------|---------|------------------------|-----------|
| CT1 | Nome com mais de 100 caracteres | O sistema exibe que o nome deve ter no máximo 100 caracteres, o formulário continua aberto e a matéria não é criada | PASS |
| CT2 | Nome com 2 caracteres | O cadastro é rejeitado, com aviso de mínimo de 3 caracteres | **FAIL** |
| CT3 | Somente os campos obrigatórios | A matéria é criada, aparece na listagem sem descrição e continua lá após atualizar a página | PASS |
| CT4 | Nome só com números e símbolos | O cadastro é rejeitado, com aviso de que o nome precisa ter pelo menos uma letra | **FAIL** |
| CT5 | Nome vazio | O campo Nome é obrigatório, o formulário continua aberto com a descrição preservada e nenhuma matéria é criada | PASS |

### Sobre os testes que falham

Os testes verificam o **comportamento esperado** descrito no plano de teste. O CT2 e o CT4 falham porque o Papyrus aceita nomes com menos de 3 caracteres e nomes sem nenhuma letra: não existe essa validação nem no frontend nem no backend. Esses FAILs são os defeitos encontrados e correspondem aos casos registrados como **NOK** no plano de teste.

## Execução

Com o Papyrus rodando em `http://localhost:5173` (frontend e backend) e o perfil de teste cadastrado, os testes são executados dentro da pasta `papyrus_testes` com:

```bash
robot -d results papyrus_testes.robot
```

O Robot gera em `results/` o `report.html` (resumo), o `log.html` (detalhamento passo a passo) e as capturas de tela de cada teste.

Antes de uma nova execução, as matérias `Estrutura de Dados`, `AB` e `1234@@@` devem ser excluídas do perfil de teste, porque o CT3 confere que "Estrutura de Dados" ainda não existe, e o CT2 e o CT4 acabam criando as outras duas por causa dos defeitos.
