import { API_URL } from "../../config/apiUrl.js";
import { convertFormToJson } from "../../util/convertFormToJson.js"
import { SessionToken } from "../../util/sessionToken.js";
import {sendRequest} from "../../util/sendRequest.js";

/**
* adiciona um listener no evento de submit do formulário
*/
document.getElementById("authForm").addEventListener("submit",
    function (event) {
    event.preventDefault(); // cancela o comportamento padrão do submit
    sendAuthRequest(event.target);
})
/**
 * Envia uma requisição de autenticação para a API.
 *
 * @param {HTMLFormElement} authRequestForm - Formulário contendo os dados da requisição.
 */
async function sendAuthRequest(authRequestForm) {

    try {

        // Converte o formulário HTML em JSON.
        const json = convertFormToJson(authRequestForm);

        // Recupera o endpoint definido no atributo action do formulário.
        const endpoint = authRequestForm.getAttribute("action");

        // Constrói a URL completa da API.
        const url = API_URL + endpoint;

        // Converte o corpo da resposta para um objeto JavaScript.
        const responseJson = await sendRequest(url, "POST", null, json)

        // Persiste o token de acesso retornado pela API
        new SessionToken().saveToken(responseJson["token"]);

        // Redireciona para a página inicial da conta.
        window.location.href = "../../account/pages/accountPage.html";

    } catch (err) {
        console.error("Requisição de autenticação falhou:", err);
    }
}