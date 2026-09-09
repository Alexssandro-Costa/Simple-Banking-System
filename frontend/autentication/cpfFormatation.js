
function formatCpf() {

    /*

    Formata o valor do cpf, a medida que caracteres são inseridos. 

    */

    try{
        
        let element = document.getElementById("cpf");

        //escuta o evento keydown no campo
        element.addEventListener('keydown', function(event){

             let tam = element.value.length;

            // verifica se o evento está apagando ou inserindo caracteres
            if(event.key !== 'Backspace') {

                if((tam == 3 || tam == 7)){
                    element.value += ".";
                }
                else if(tam == 11) {
                    element.value += "-";
                }
            }
        })
    }catch(error) {
        console.error(error)
    }
}