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
  let pageNum = 1;
  const quantity = 5;

  showStatement(pageNum, quantity);

  // adiciona um listener nos botões next e previous da seção de statement
  const next = document.getElementById("nextStatement");
  const previous = document.getElementById("previousStatement");

  next.addEventListener("click", function () {
    pageNum += 1;
    showStatement(pageNum, quantity);
  });

  previous.addEventListener("click", function () {
    if (pageNum > 1) {
      pageNum -= 1;
      showStatement(pageNum, quantity);
    }
  });
}

/**
 * Modifica a seção content da pagina principal para mostrar os dados da requisição de extratos
 */
async function showStatement(pageNum, quantity) {
  // recupera os extratos paginados da API
  let responseJson = await getPageableStatement(pageNum, quantity);

  try {
    // modifica a seção de conteudo
    let content = document.getElementById("content");
    content.innerHTML = "";

    for (const transaction of responseJson) {
      content.innerHTML += `
            <h3>Transação</h3>
            <p>Tipo: ${transaction.type}</p>
            <p>ID: ${transaction.transactionID} </p>
            <p>Valor: ${transaction.amount}</p>
            <p>Destino:  ${transaction.destination}</p>
            <p>Data de Emissão: ${new Date(transaction.emissionDate).toLocaleString("pt-br")}</p>
            <hr>
            `;
    }

    // adiciona os botões de controle de paginação
    content.innerHTML += `
      <nav>
        <button id="previousStatement">Anterior</button>
        <button id="nextStatement">Proximo</button>
      </nav>
    `
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
export async function getPageableStatement(pageNumber, itemQuantity) {
  const endpoint = "/api/operations/account/statement";
  const url = API_URL + endpoint;

  const body = JSON.stringify({
    pageNumber: pageNumber,
    itemQuantity: itemQuantity,
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
