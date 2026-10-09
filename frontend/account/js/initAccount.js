import { setHomeContent } from "./home.js";
import { getAccountData } from "./updateHeaderData.js";

// Chama a função GetAccountData durante o carregamento da pagina
document.addEventListener("DOMContentLoaded", function () {
    getAccountData();
    setHomeContent();
});

