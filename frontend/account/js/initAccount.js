import { GetAccountData } from "./GetAccountData.js";
import { setHomeContent } from "./home.js";

// Chama a função GetAccountData durante o carregamento da pagina
document.addEventListener("DOMContentLoaded", function () {
    GetAccountData();
    setHomeContent();
});

