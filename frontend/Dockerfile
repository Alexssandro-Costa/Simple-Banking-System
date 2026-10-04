# Usar a imagem oficial do Nginx
FROM nginx:alpine

# Instalar envsubst
RUN apk add --no-cache gettext

# Definir o diretório de trabalho dentro do container
WORKDIR /usr/share/nginx/html

# Remover arquivos padrão do Nginx
RUN rm -f /usr/share/nginx/html/index.html

# Copiar os arquivos HTML, CSS e JS para o container
COPY ./ /usr/share/nginx/html

# Expor a porta padrão do Nginx
EXPOSE 80

# Gera o  config.js e iniciar o Nginx
CMD ["sh", "-c", "envsubst '${API_URL}' < config/config.js.template > config/config.js && nginx -g 'daemon off;'"]