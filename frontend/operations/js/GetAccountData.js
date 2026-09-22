import { sendAuthenticatedRequest } from "../../util/sendAuthenticatedRequest.js";
import { SessionToken } from "../../util/sessionToken.js";


document.addEventListener("DOMContentLoaded", function () {
    GetAccountData();
});



// TO DO: Separa as funcionalidades
/**
 * recupera os dados da conta bancaria através de uma requisição http e modifica os elementos da pagina
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

        console.log(json);
        console.log(json["name"]);

        // modifica o valor dos elementos da pagina
        document.getElementById("accountNumber").textContent = json["accountNumber"];
        document.getElementById("userName").textContent = json["name"]; 
        document.getElementById("accountBalance").textContent = json["balance"];

    }
    catch (error) {
        console.log("Erro ao buscar dados da conta: " + error);
    }

}