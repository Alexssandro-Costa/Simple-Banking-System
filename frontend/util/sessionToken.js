export class SessionToken {

    /**
     * Armazena o token de acesso da sessão atual.
     *
     * @param {string} token - Token JWT.
     */
    saveToken(token) {
        sessionStorage.setItem("token", token);
    }

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