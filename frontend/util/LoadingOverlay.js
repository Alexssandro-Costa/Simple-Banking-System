
/**
 * Mostra a animação de loading na tela
 */
export function showLoading() {
    JsLoadingOverlay.show({
        "overlayBackgroundColor": "#666666",
        "overlayOpacity": 0.6,
        "spinnerIcon": "ball-pulse",
        "spinnerColor": "#000",
        "spinnerSize": "3x",
        "overlayIDName": "overlay",
        "spinnerIDName": "spinner",
        "offsetX": 0,
        "offsetY": 0,
        "containerID": null,
        "lockScroll": true,
        "overlayZIndex": 9998,
        "spinnerZIndex": 9999
    });
}

/**
 * Esconde a animação de loading
 */
export function hideLoading() {
    JsLoadingOverlay.hide();
}