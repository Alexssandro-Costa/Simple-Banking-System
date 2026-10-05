## importa uma imagem estável do maven
FROM maven:3.9-eclipse-temurin-21 as build

## passa pra pasta onde será movido os arquivos do projeto
WORKDIR /builder

## copia os arquivos do contexto de build para a imagem
COPY . .

## gera o arquivo compilado da api
## -DskipTests: pula a execução dos testes.
## --batch-mode: executa o maven em modo não interativo.
RUN mvn clean package -DskipTests --batch-mode

# importa uma imagem mais leve contendo a JRE para executar a aplicação
FROM eclipse-temurin:21-jre

# muda pro diretorio da aplicação
WORKDIR /app

## copia o arquivo .jar compilado, para dentro da imagem
COPY --from=build /builder/target/*.jar  app.jar

## define a porta que a imagem irá rodar no container
EXPOSE 8080

## Define o arquivo compilado que deve rodar na maquina
ENTRYPOINT ["java", "-jar", "app.jar"]