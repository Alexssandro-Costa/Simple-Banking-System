import { API_URL } from "../../config/apiUrl.js";
import { sendAuthenticatedRequest } from "../../util/sendAuthenticatedRequest.js";
import { SessionToken } from "../../util/sessionToken.js";

// TO DO: Separar as funcionalidades
/**
 * recupera os dados da conta bancaria através de uma requisição http
 */
export async function GetAccountData() {

    try {

        // url da APi
        const endpoint = "/api/operations/account/data";
        const url = API_URL + endpoint;

        // busca o token de acesso
        const token = new SessionToken().getToken();

        // dados recuperados da requisição
        const json = await sendAuthenticatedRequest(url, "POST", token, null);

        // modifica o valor dos elementos da pagina
        updateAccountNumberHeader(json["accountNumber"]);
        updateUserNameHeader(json["userName"]);
        updateBalanceHeader(json["balance"]);

    }
    catch (error) {
        console.log("Erro ao buscar dados da conta: " + error);
    }

}

/**
 * Atualiza o Número da conta no header da pagina da conta
 * @param {*} accountNumber Conteudo que será inserido no elemento
 */
async function updateAccountNumberHeader(accountNumber) {
    document.getElementById("accountNumber").textContent = "Número da Conta: " + accountNumber;
}

/**
 * Atualiza o nome do usúario no header da pagina da conta.
 * @param {*} userName  conteudo que será inserido no elemento
 */
async function updateUserNameHeader(userName) {
    document.getElementById("userName").textContent = "Nome do Titular: " + userName;
}


/**
 * Atualiza o nome do usúario no header da pagina da conta.
 * @param {*} balance conteudo que será inserido no elemento
 */
export async function updateBalanceHeader(balance) {
    document.getElementById("accountBalance").textContent = "Saldo: " + balance;
}