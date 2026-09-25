import { sendAuthenticatedRequest } from "../../util/sendAuthenticatedRequest.js";
import { SessionToken } from "../../util/sessionToken.js";


// Chama a função GetAccountData durante o carregamento da pagina
document.addEventListener("DOMContentLoaded", function () {
    GetAccountData();
});



// TO DO: Separa as funcionalidades
/**
 * recupera os dados da conta bancaria através de uma requisição http e Insere os dados da conta no header da pagina
 */
async function GetAccountData() {

    try {
        // url da APi
        const url = "http://localhost:8080/api/operations/account/data";

        // busca o token de acesso
        const session = new SessionToken();
        const token = session.getToken();

        // dados recuperados da requisição
        const json = await sendAuthenticatedRequest(url, "POST", token, null);

        // modifica o valor dos elementos da pagina
        document.getElementById("accountNumber").textContent = "NÚMERO DA CONTA: " + json["accountNumber"];
        document.getElementById("userName").textContent = "NOME: " + json["name"]; 
        document.getElementById("accountBalance").textContent = "SALDO: " + json["balance"];

    }
    catch (error) {
        console.log("Erro ao buscar dados da conta: " + error);
    }

}