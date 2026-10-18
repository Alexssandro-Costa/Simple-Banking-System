import { API_URL } from "../../config/apiUrl.js";
import { SessionToken } from "../../util/sessionToken.js";
import { sendAuthenticatedRequest } from "../../util/sendAuthenticatedRequest.js";

document
  .getElementById("statementButton")
  .addEventListener("click", function () {
    startStatementSection();
  });

/**
 * Inicia a seção de extrato bancario e os listeners utilizados
 */
async function startStatementSection() {

  // numero da pagina buscada
  let pageNum = 0;
  // quantidade de itens buscados
  const quantity = 5;

  const content = document.getElementById("content");

  // apaga qualquer elemento restante da seção de conteudo
  content.innerHTML = "";

  // inicializa a seção de conteudo
  content.innerHTML = `

    <section id="statement"></section>

    <nav>
      <button id="previousPage">Anterior</button>
      <button id="nextPage">Proximo</button>
    </nav>
  `;

  // listener para o botão de proxima pagina
  document
    .getElementById("nextPage")
    .addEventListener("click", function (event) {
      // verifica se o botão não está desabilitado
      if (!event.target.disabled) {
        pageNum += 1;
        showStatement(pageNum, quantity);
      }
    });

  // listener para o botão de pagina anterior
  document
    .getElementById("previousPage")
    .addEventListener("click", function (event) {
      // verifica se o botão não está desabilitado
      if (!event.target.disabled) {
        pageNum -= 1;
        showStatement(pageNum, quantity);
      }
    });

    // mostra o extrato na tela
    await showStatement(pageNum, quantity);
}

/**
 * Modifica a seção content da pagina principal para mostrar os dados da requisição de extratos
 */
async function showStatement(pageNum, quantity) {
  // recupera os dados da API
  let responseJson = await getPageableStatement(pageNum, quantity);

  // recupera o extrato da resposta
  let transactions = responseJson["statement"];

  try {

    const statement = document.getElementById("statement");
    statement.innerHTML = ""; // apaga as informações que ficaram no elemento.

    // insere as transações na pagina
    for (const transaction of transactions) {
      statement.innerHTML += `
            <h3>Transação</h3>
            <p>Tipo: ${transaction.type}</p>
            <p>ID: ${transaction.transactionID} </p>
            <p>Valor: ${transaction.amount}</p>
            <p>Destino:  ${transaction.destination}</p>
            <p>Data de Emissão: ${new Date(transaction.emissionDate).toLocaleString("pt-br")}</p>
            <hr>
            `;
    }

    // modifica o estado dos botões 
    updatePaginationControls(responseJson["isFirst"], responseJson["isLast"]);

  } catch (err) {
    console.error("Erro ao mostrar extrato: " + err);
  }
}

/**
 *  Busca os extratos bancarios da conta logada
 * @returns Array de Strings contendo os dados da requisição em formato JSON.
 * @param {*} pageNumber Número da pagina
 * @param {*} itemQuantity Quantidade de itens que devem ser retornados pela API
 */
async function getPageableStatement(pageNumber, itemQuantity) {
  const endpoint = "/api/operations/account/statement";
  const url = API_URL + endpoint;

  const body = JSON.stringify({
    pageNumber: pageNumber,
    quantity: itemQuantity,
  });

  // recupera o token da sessão
  const token = new SessionToken().getToken();

  try {
    if (url === null || token === null) {
      throw new Error("Token ou url invalidos");
    }

    return await sendAuthenticatedRequest(url, "POST", token, body);
  } catch (err) {
    console.error("Erro ao buscar extrato: " + err);
  }
}

/**
 * Atualiza estado dos botões de paginação com base no estado da pagina retornada na paginação
 * @param {boolean} isFirst pagina retornada é a primeira na paginação
 * @param {boolean} isLast pagina retornada é a ultima na paginação
 */
async function updatePaginationControls(isFirst, isLast) {
  document.getElementById("previousPage").disabled = isFirst;
  document.getElementById("nextPage").disabled = isLast;

}



