

const opButton = document.getElementById("operationsButton");
opButton.addEventListener("click", function () {

    // chama a função após o botão de operações ser chamado
    defineTransactionForm();

    // adiciona um listener no tipo da transação
    document.getElementById("transactionType").addEventListener("input", function (event) {
        defineTransactionTypesFeatures(event.target);
    });

    // adiciona um listener no envio da transação
    document.addEventListener("submit", function (event) {
        event.preventDefault(); // cancela o comportamento padrão do submit
        sendTransaction(event.target);
    })

});



import { SessionToken } from "../../util/sessionToken.js";
import { convertFormToJson } from "../../util/convertFormToJson.js";
import { sendAuthenticatedRequest } from "../../util/sendAuthenticatedRequest.js";
/**
 * Realiza a requisição de transação bancaria
 * @param {HTMLFormElement} transactionForm - formulario html 
 */
async function sendTransaction(transactionForm) {

    try {
        // busca o token de acesso da sessão
        let token = new SessionToken().getToken();

        // recupera a url da requisição no formulario
        let url = transactionForm.attributes["action"].value;

        // converte o elemento html em um json
        let jsonForm = convertFormToJson(transactionForm);

        if (token === null || url === null || jsonForm === null) {
            console.error("Não foi possivel recuperar os elementos");
            return;
        }

        // espera a promise da função e mostra os dados na tela
        let responseJson = await sendAuthenticatedRequest(url, "POST", token, jsonForm);
        showTransactionData(responseJson);

    } catch (error) {
        console.error("Erro ao enviar transação: " + error);
    }

}


/**
 * Adiciona um formulario html no lugar do elemento de conteudo
 */
async function defineTransactionForm() {

    try {
        // recupera a seção do documento que será modificada
        let section = document.getElementById("content");

        // adiciona um fomulario html na seção do documento
        section.innerHTML = `
    <form id="form" name="form" action="http://localhost:8080/api/operations/account/transaction">
        <fieldset>
            <legend>Transação</legend>
            
            <label for="transactionType">Tipo da transação:</label>
            <select id="transactionType" name="transactionType">
                <option value="TRANSFERENCIA">Transferência</option>
                <option value="DEPOSITO">Deposito</option>
                <option value="SAQUE">Saque</option>
            </select>

            <br>

            <label for="receiver">Destinatario: </label>
            <input id="receiver" name="receiver"
            type="text" required
            placeholder="Destino da Transação"
            >

            <br>

            <label for="sender">Remetente: </label>
            <input id="sender" name="sender"
            type="text" required
            placeholder="Origem da Transação"
            >

            <br>

            <label for="value">Valor da transação: </label>
            <input id="value" name="value"
            type="text" required
            placeholder="Valor da Transação"
            >
        </fieldset>

        <fieldset>
            <button type="submit">Realizar Transação</button>
            <button type="reset">Apagar Transação</button>
        </fieldset>
    
    </form>
    
    
    `;

    }
    catch (error) {
        console.error("Erro ao inserir formulario na pagina: " + error);
    }

}

/**
 * Modifica um section para mostrar os dados de uma transação na tela
 * @param {*} json json de resposta com os dados da transação
 */
async function showTransactionData(json) {

    try {
        // recupera a seção do documento que será modificada
        let section = document.getElementById("content");

        if (section === null) {
            throw new Error("Não foi possivel encontrar a seção");
        }

        // modifica a seção com os dados recebidos
        section.innerHTML = `

            <h2>Transação realizada</h2>

            <p>Tipo: ${json["type"]}</p>
            <p>ID: ${json["transactionId"]}</p>
            <p>Valor: ${json["amount"]}</p>
            <p>Novo Saldo: ${json["newBalance"]}</p>
            <p>Destino: ${json["destination"]}</p>
            <p>Data de Emissão: ${new Date(json["emissionDate"]).toLocaleString("pt-br")}</p>         
        `;


    } catch (error) {
        console.log("ERRO! Não foi possivel mostrar a requisição: " + error);
    }


}


/**
 * Define as caracteristicas que cada tipo de transação deve ter.
 * Exemplo: Definir o destinatario de uma transação de saque como externo.
 * @param {*} transactionType - Elemento HTML que define o tipo de transação
 */
async function defineTransactionTypesFeatures(transactionType) {

    try {

        let sender = document.getElementById("sender");
        let receiver = document.getElementById("receiver");
        const accountNum = document.getElementById("accountNumber").innerText;

        if (sender === null || receiver === null || accountNum === null) {
            console.error("Campos não encontrados.");
            return;
        }

        if (transactionType.value == "TRANSFERENCIA") {

            // apaga o valor anterior dos campos e os habilita para inserção de dados

            sender.readOnly = false;
            sender.value = "";

            receiver.readOnly = false;
            receiver.value = "";

        } else {

            // permite a modificação dos dados dos elementos
            receiver.readOnly = false;
            sender.readOnly = false;

            if (transactionType.value == "DEPOSITO") {
                sender.value = "EXTERNO";
                receiver.value = accountNum;
            }
            else if (transactionType.value == "SAQUE") {
                sender.value = accountNum;
                receiver.value = "EXTERNO";
            }

            // bloqueia a modificação dos dados dos elementos
            sender.readOnly = true;
            receiver.readOnly = true;
        }

    }
    catch (error) {
        console.error("Error ao setar as caracteristicas do tipo de transação: " + error);
    }
}

