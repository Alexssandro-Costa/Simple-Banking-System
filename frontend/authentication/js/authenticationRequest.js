import { API_URL } from "../../config/apiUrl.js";
import { convertFormToJson } from "../../util/convertFormToJson.js"
import { SessionToken } from "../../util/sessionToken.js";

/**
* Captura o evento de submit do formulario e modifica seu evento para chamar sendAuthRequest; 
*/
const form = document.getElementById("authForm");
form.addEventListener("submit", function (event) {
    event.preventDefault(); // cancela o comportamento padrão do submit

    sendAuthRequest(form);
})
/**
 * Envia uma requisição de autenticação para a API.
 *
 * @param {HTMLFormElement} authRequestForm - Formulário contendo os dados da requisição.
 */
async function sendAuthRequest(authRequestForm) {

    // Converte o formulário HTML em JSON.
    const json = convertFormToJson(authRequestForm);

    // Recupera o endpoint definido no atributo action do formulário.
    const endpoint = authRequestForm.getAttribute("action");

    // Verifica se os dados necessários para a requisição são válidos.
    if (!endpoint || !json) {
        throw new Error("Endpoint ou JSON inválidos.");
    }

    // Constrói a URL completa da API.
    const url = API_URL + endpoint;

    try {
        // Envia a requisição para a API.
        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: json
        });

        // Trata respostas HTTP que indicam erro.
        if (!response.ok) {

            const errorJson = await response.json();

            console.error("Erro retornado pela API:", errorJson);

            throw new Error("Erro HTTP: " + response.status);
        }

        // Converte o corpo da resposta para um objeto JavaScript.
        const responseJson = await response.json();

        // Persiste o token de acesso.
        const session = new SessionToken();
        session.saveToken(responseJson["token"]);

        // Redireciona para a página inicial da conta.
        window.location.href = "../../account/pages/accountPage.html";

    } catch (err) {
        console.error("Requisição de autenticação falhou:", err);
    }
}