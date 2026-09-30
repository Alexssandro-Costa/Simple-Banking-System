
import { getStatement } from "./statement.js";

document.getElementById("homeButton").addEventListener("click", function () {
    setHomeContent();
});

/**
 * Modifica o contéudo da section passada 
 */
async function setHomeContent() {

    try {

        let section = document.getElementById("content");

        if (section == null) {
            throw new Error("A section de id: " + section.id + ", não existe!");
        }

        // adiciona um elemento canvas dentro do section para utilização de gráficos
        section.innerHTML = "<canvas id='graph' ></canvas>";

        // define o gráfico na pagina
        await definePieChart("graph")

    } catch (error) {
        console.error("Erro ao modificar contéudo da home: " + error);
    }

}

/**
 * Insere um gráfico de pizza/pie no contexto especificado
 * @param {string} context - id do elemento canvas que receberá o gráfico 
 */
async function definePieChart(context) {

    if (context === null) {
        throw new Error("Contexto especificado para definição do gráfico é nulo");
    }

    // recupera os dados filtrados do extrato
    const amountByType = await filterStatementAmount(); 

    // define os dados do gráfico
    const data = {
        labels: ["DEPOSITO", "SAQUE", "TRANSFERÊNCIA"],
        datasets: [
            {
                label: "Total movimentado R$",
                data: [amountByType.get("DEPOSITO"), amountByType.get("SAQUE"), amountByType.get("TRANSFERENCIA")]
            }
        ]
    };

    // define as configurações do gráfico
    const config = {
        type: 'pie',
        data: data,
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: 'Transações'
                }
            }
        },
    };

    // define o grafico na  pagina
    new Chart(context, config);

}


/**
 * Filtra os dados do extrato bancario
 * @returns retorna uma promise de um Map mapeado em: 
 * key(tipo da transferência) / value(valor total movimentado de cada tipo) 
 */
async function filterStatementAmount() {

    // recupera o extrato bancario
    const statementData = await getStatement();

    const typeMap = new Map([
        ["DEPOSITO", 0],
        ["SAQUE", 0],
        ["TRANSFERENCIA", 0]
    ]);

    // atualiza o valor do map
    for (const transaction of statementData) {

        const type = transaction.type;
        typeMap.set(type, typeMap.get(type) + Number(transaction.amount));
    }

    return typeMap;

}



