import {showLoading, hideLoading} from "./LoadingOverlay.js";
import {API_URL} from "../config/apiUrl.js";

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
        showLoading();

        const response = await fetch(url, {
            method: "GET"
        });

        if(!response.ok){
            throw new Error("Error ao tentar verificar saúde da API");
        }

        alert(response.json());
    }
    catch (err) {
        alert(err);
    }
    finally {
        setTimeout(() => hideLoading(), 1000);
    }
}