const elem = document.getElementById("registerForm");
elem.addEventListener("input", function (event) {
    autoComplete(elem, event);
});


/**
 * Metodo temporario utilizado para autocompleção dos campos do formulario de registro 
 * @param {HTMLFormElement} form formulario para autocompleção
 */
function autoComplete(form, event) {


    form.elements["name"].value = "Primeiro Teste";

    form.elements["cpf"].value = "111.111.111-11";

    form.elements["phone"].value = "22992763231";

    form.elements["password"].value = "Teste11111";

}