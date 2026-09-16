
/**
 * Classe que armazena o token de acesso da sessão atual.
 */
export class sessionToken {

    /**
     * @param {string} tokenJson - json do token de acesso 
     */
    constructor(tokenJson) {
        let token = sessionStorage.setItem("token", tokenJson["token"]);
    }

}