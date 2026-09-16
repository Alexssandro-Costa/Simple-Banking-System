
/**
* Captura o evento de input do campo cpf 
*/
const cpf = document.getElementById("cpf");
cpf.addEventListener("input", function() {
    formatCpf(cpf);
});

    /** 
     * Formata o valor do cpf, a medida que caracteres são inseridos. 
     * @param {any} cpf - valor do cpf que será modificado
    */
function formatCpf(cpf) {

    // Remove tudo que não seja número
    let value = cpf.value.replace(/\D/g, "");

    // limita a 11 numeros
    value = value.substring(0, 11);

    // adiciona um ponto no primeiro grupo de 3 caracteres
    if (value.length > 9) {
        value = value.replace(/^(\d{3})(\d{3})(\d{3})(\d{1,2}).*/, "$1.$2.$3-$4");
    }
    // adiciona um ponto no segunto grupo de 3 caracteres
    else if (value.length > 6) {
        value = value.replace(/^(\d{3})(\d{3})(\d{1,3}).*/, "$1.$2.$3");
    } 
    // adiciona um hífen no ultimo grupo de caracteres
    else if (value.length > 3) {
        value = value.replace(/^(\d{3})(\d{1,3}).*/, "$1.$2");
    }

    cpf.value = value;
}