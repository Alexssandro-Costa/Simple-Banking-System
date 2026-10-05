export class SessionToken {

    /**
     * Armazena o token de acesso da sessão atual.
     *
     * @param {string} token - Token JWT.
     */
    saveToken(token) {

        if(token === null || token === undefined) {
            throw new Error("token passado é invalido");
        }
        
        sessionStorage.setItem("token", token);
    }

    SessionToken(){}

    /**
     * Recupera o token de acesso da sessão atual.
     *
     * @returns {string|null} Token JWT ou null caso não exista.
     */
    getToken() {
        return sessionStorage.getItem("token");
    }

    /**
     * Remove o token da sessão atual.
     */
    removeToken() {
        sessionStorage.removeItem("token");
    }
}