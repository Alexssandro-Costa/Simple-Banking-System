
/**
 * Insere um gráfico no contexto especificado
 * @param {string} context id do elemento canvas que receberá o gráfico
 * @param {string } type tipo do gráfico
 * @param data dados que devem ser inseridos no gráfico
 * @param {string} title titulo do gráfico
 */
export async function defineChart(context, type, data, title) {

    // define as configurações do gráfico
    const config = {
        type: type,
        data: data,
        options: {
            responsive: true,
            plugins: {
                legend: {
                    display: true,
                    position: 'top',
                },
                title: {
                    display: true,
                    text: title
                }
            }
        }
    };

    // define o grafico na  pagina
    new Chart(context, config);
}
