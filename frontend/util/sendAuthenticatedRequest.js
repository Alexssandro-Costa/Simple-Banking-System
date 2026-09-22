
/**
 * Envia uma requisição assincrona com um token jwt autenticado para a url desejada 
 * @param {string} url endereço da requisição
 * @param {string} token token de acesso jwt
 * @param {string} method metodo que será utilizado
 * @param {string|null} requestJson  
 * @returns {Promise<Object|null>} Retorna uma string em formato JSON contendo os dados de retorna da requisição
 * ou nulo caso não exista
 */
export async function sendAuthenticatedRequest(url, method, token, requestJson) {

    // envia a requisição 
    const response = await fetch(
        url, {
        method: method,
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token // envia o token no cabeçalho 
        },
        body: requestJson
    });

    // testa a requisição http
    if (!response.ok) {
        throw new Error("Erro HTTP: " + response.status);
    }

    // converte e retorna o json da resposta
    const responseJson = await response.json();

    console.log(responseJson);

    return responseJson;



}


