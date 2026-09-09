import {convertFormToJson} from "../convertFormToJson"

/**
 * Envia uma requisição de autenticação pra API.
 * @param {Form} authRequestForm - Formulario contendos os dados da requisição
 */
function sendAuthRequest(authRequestForm) {

    let url = "https://sistema-bancario-simplificado.onrender.com/auth/register"

    const json = convertFormToJson(authRequestForm);

    // envia a requisição pra API
    fetch(url, {
        method:"POST", 
        headers: {"Content-Type" : "application/json"},
        body: json
    });
}