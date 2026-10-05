/**
 * Converte os dados de um formulário em JSON.
 * @param {HTMLFormElement} form - Formulário que será convertido.
 * @returns {string} String contendo os dados do formulário em formato JSON.
 */
export function convertFormToJson(form) {

    // Converte os dados do formulário em pares chave/valor.
    const formData = new FormData(form);

    // Converte os pares chave/valor em um objeto JavaScript.
    const obj = Object.fromEntries(formData);

    // Converte o objeto JavaScript em uma string JSON.
    const json = JSON.stringify(obj, null, 2);
    
    return json;
}