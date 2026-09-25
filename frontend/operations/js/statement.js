import { SessionToken } from "../../util/sessionToken.js";
import { sendAuthenticatedRequest } from "../../util/sendAuthenticatedRequest.js";

document.getElementById("statementButton").addEventListener("click", function () {
    showStatement();
});


/**
 * Modifica a seção content da pagina principal para mostrar os dados requisição de extratos
 */
async function showStatement() {

    // recupera os extratos
    let responseJson = await getStatement();

    try {

        // modifica o 
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
            <br>
            `
        }


    } catch (err) {
        console.error("Erro ao mostrar extrato: " + err);
    }
}

/**
 *  Busca os extratos bancarios da conta logada
 * @returns String contendo os dados da requisição em formato JSON.
 */
async function getStatement() {

    const url = "http://localhost:8080/api/operations/account/statement";

    // recupera o token da sessão
    const token = new SessionToken().getToken();

    try {

        if (url === null || token === null) {
            throw new Error("Token ou url invalidos");
        }

        return await sendAuthenticatedRequest(url, "POST", token, null);

    } catch (err) {
        console.error("Erro ao buscar extrato: " + err);
    }

}