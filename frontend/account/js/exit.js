import { SessionToken } from "../../util/sessionToken.js";

document.getElementById("exitButton").addEventListener("click", function () {
    exitAccount();
})

async function exitAccount() {

    // remove o token de acesso da sessão
    const session = new SessionToken().removeToken();

    // redireciona o usuario para a pagina de login
    window.location.href = "../../authentication/pages/loginWindow.html";
}