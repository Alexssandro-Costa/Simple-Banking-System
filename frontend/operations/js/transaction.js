


const opButton = document.getElementById("operationsButton");
opButton.addEventListener("click", function () {

    // // chama a função após o botão de operações ser chamado
    defineTransactionForm();

    // adiciona um listener no tipo da transação
    document.getElementById("transactionType").addEventListener("input", function (event) {
        defineTransactionTypesFeatures(event.target);
    });

});

/**
 * Adiciona um formulario html no lugar do elemento de conteudo
 */
async function defineTransactionForm() {

    try {
        // recupera a seção do documento que será modificada
        let section = document.getElementById("content");

        // adiciona um fomulario html na seção do documento
        section.innerHTML = `
    <form>
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