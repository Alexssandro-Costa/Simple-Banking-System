import {showLoading, hideLoading} from "./LoadingOverlay.js";
import {API_URL} from "../config/apiUrl.js";
import {sendRequest} from "./sendRequest.js";

document.addEventListener("DOMContentLoaded", function (){
    testHealthEndpoint();
});

/**
 * faz uma requisição ao endpoint /actuator/health da API para verificar a sua disponibilidade
 */
async function testHealthEndpoint() {
    const  endpoint = "/actuator/health";
    const url = API_URL + endpoint;

    try{
        // mostra a animação de loading
        showLoading();

        // realiza a requisição
        await sendRequest(url, "GET", null, null);
    }
    catch (err) {
        console.error(err);
    }
    finally {
        setTimeout(() => hideLoading(), 1000);
    }
}