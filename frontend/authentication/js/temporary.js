const elem = document.getElementById("authForm");
elem.addEventListener("input", function () {
    autoComplete(elem);
});


/**
 * Metodo temporario utilizado para autocompleção dos campos do formulario de registro 
 * @param {HTMLFormElement} form formulario para autocompleção
 */
function autoComplete(form) {


    form.elements["name"].value = "Teste dois";

    form.elements["cpf"].value = "222.222.222-22";

    form.elements["phone"].value = "22992763231";

    form.elements["password"].value = "Teste222";

}