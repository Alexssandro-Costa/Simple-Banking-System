
/**
 * Envia uma requisição assincrona com um token jwt autenticado para a url desejada 
 * @param {string} url endereço da requisição
 * @param {string} token token de acesso jwt
 * @param {string} method metodo HTTP que será utilizado que será utilizado
 * @param {string|null} requestJson  Corpo da requisição em formato JSON
 * @returns {Promise<Object|null>} Retorna uma string em formato JSON contendo os dados de retorna da requisição
 * ou nulo caso não exista
 */
export async function sendAuthenticatedRequest(url, method, token, requestJson) {

    try {
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

        // Trata respostas HTTP que indicam erro.
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error("Erro HTTP: " + response.status + " - " + errorText);
        }

        // retorna o objeto json de resposta
        return await response.json();

    } catch (err) {
        console.error("Erro ao enviar requisição autenticada: " + err);
    }

}


