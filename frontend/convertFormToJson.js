

/** 
 * Converte os dados de um formulário em JSON.
 * @param {Form}form - Formulario que será convertido.
 * @returns {string} String contendo os dados do formulário em formato JSON. 
*/
export function convertToJson(form) {

    // Seleciona o formulário pelo ID "register" e transforma
    // seus campos em um array de objetos.
    const formArray = $(form).serializeArray();

    // Cria um objeto JavaScript vazio, que será usado para armazenar 
    // os dados do formulário no formato chave → valor.
    const obj = {};

    // Percorre cada elemento do array criado pelo serializeArray().
    formArray.forEach(element => {

        // Cria uma propriedade no objeto usando o nome
        // do campo do formulário como chave.
        obj[element.name] = element.value;
    });

    // Converte o objeto JavaScript em uma string JSON.
    const json = JSON.stringify(obj, null, 2);
    
    return json;
}