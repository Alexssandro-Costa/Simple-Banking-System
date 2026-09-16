import {convertFormToJson} from "../../convertFormToJson.js"

/**
* Captura o evento de submit do formulario e modifica seu evento para chamar sendAuthRequest; 
*/
const form = document.getElementById("registerForm");
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
        console.log("Status:", response.status);
        console.log("OK:", response.ok);

        return response.text();
    })
    .then(function(body) {
        console.log("Resposta da API:", body);
    })
    .catch(function(err) {
        console.error("Erro na requisição:", err);
    });
    
}