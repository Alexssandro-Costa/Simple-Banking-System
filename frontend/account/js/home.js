import {sendAuthenticatedRequest} from "../../util/sendAuthenticatedRequest.js";
import {API_URL} from "../../config/apiUrl.js";
import {SessionToken} from "../../util/sessionToken.js";
import {defineChart} from "../../util/defineChart.js";


document.getElementById("homeButton").addEventListener("click", function () {
    setHomeContent();
});

/**
 * Modifica o contéudo da section passada 
 */
export async function setHomeContent() {

    try {
        // recupera a sessão de dados do documento
        let section = document.getElementById("content");

        if (section == null) {
            throw new Error("A section de id: \"content\", não existe!");
        }

        // adiciona um elemento canvas dentro do section para utilização de gráficos
        section.innerHTML = "<canvas id='pieChart' ></canvas>";

        // recupera os dados filtrados da API
        let data = await filterStatisticsForPieChart();

        // define um gráfico de pizza/pie na sessão
        await defineChart("pieChart", "pie", data, "Transações por Tipo")

    } catch (error) {
        console.error("Erro ao modificar contéudo da home: " + error);
    }

}


/**
 * Filtra dados estatísticos recuperados da API para serem utilizados em gráficos de pizza/pie
 * @returns {Promise<{labels: *[], datasets: [{label: string, data: *[]}]}>} Array contendo dados filtrados
 */
async function filterStatisticsForPieChart() {

    // recupera os dados brutos da api
    const statistics = await getStatistics();

    // array contendo os nomes dos tipos de transação
    let labels = [];

    // array contendo os dados de cada tipo de transação
    let data = [];

    // atualiza o valor dos arrays
    for (const typeData of statistics.dataResponse) {
        labels.push(typeData.type);
        data.push(typeData.totalMoved)
    }

    // retorna os dados filtrados
    return {
        labels: labels,
        datasets: [
            {
                label: "Total movimentado R$",
                data: data
            }
        ]
    };

}

/**
 * Recupera dados estatísticos da API
 * @returns {Promise<Object|null>} dados estatísticos brutos
 */
async function getStatistics() {
    // url do endpoint da API
    const url = API_URL + "/api/account/statistics/generic"

    // recupera o token de acesso da sessão
    const token = new SessionToken().getToken();

    // recupera os dados da API
    return await sendAuthenticatedRequest(url, "GET", token, null);

}



