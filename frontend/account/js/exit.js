import { SessionToken } from "../../util/sessionToken.js";

document.getElementById("exitButton").addEventListener("click", function () {
    exitAccount();
})

/**
 * Apaga o token da sessão e sai da conta bancaria
 */
function exitAccount() {

    // remove o token de acesso da sessão
    new SessionToken().removeToken();

    // redireciona o usuario para a pagina de login
    window.location.href = "../../authentication/pages/loginWindow.html";
}