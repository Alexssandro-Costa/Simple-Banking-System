
function formatCpf() {

    /*

    Formata o valor do cpf, a medida que caracteres são inseridos. 

    */


    let element = document.getElementById("cpf");

    let tam = element.value.length;

    if(element.value.charAt(tam - 1) != "." && element.value.charAt(tam -1) != "-") {

        if(tam == 3 || tam == 7){
            element.value += "."
        }
        else if(tam == 11) {
            element.value += "-"
        }
    }
}