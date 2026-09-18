import {convertFormToJson} from "../../util/convertFormToJson.js"
import { SessionToken } from "../../util/sessionToken.js";

/**
* Captura o evento de submit do formulario e modifica seu evento para chamar sendAuthRequest; 
*/
const form = document.getElementById("authForm");
form.addEventListener("submit", function(event) {
    event.preventDefault(); // cancela o comportamento padrão do submit
    
    sendAuthRequest(form);
})

/**
 * Envia uma requisição de autenticação pra API.
 * @param {HTMLFormElement} authRequestForm - Formulario contendos os dados da requisição
 */
function sendAuthRequest(authRequestForm) {

    // converte um formulario html em um json
    const json = convertFormToJson(authRequestForm);

    // recupera a url do atributo action do formulario
    const url = authRequestForm.attributes["action"].value;

    // envia a requisição pra API
    fetch(url, {
        method:"POST", 
        headers: {"Content-Type" : "application/json"},
        body: json
    })
    .then(function(response) {

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}`);
        }
        return response.json();
    })
    .then(function(data) {
         
        // Login confirmado pelo backend
        console.log("Login realizado:", data);
        
        // persiste o token de acesso
        const session = new SessionToken();
        session.saveToken(data["token"]);

        //window.location.href = ".../operations/js/accountPage.html";

    })
    .catch(function(err) {
        console.error("Erro ao tentar entrar na conta: ", err);
    });
    
}