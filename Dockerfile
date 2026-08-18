## importa uma imagem estável do maven
from maven:3.9-eclipse-temurin-21 as build

## passa pra pasta onde será movido os arquivos do projeto
workdir /builder

## copia todos os arquivos do projeto
copy .. .

## gera o arquivo compilado da api
## -DskipTests: pula a execução dos testes.
## --batch-mode: executa o maven em modo não interativo.
run mvn clean package -DskipTests --batch-mode

## importa uma imagem mais leve do jdk para rodar a aplicação
from eclipse-temurin:21-jre

# muda pro diretorio da aplicação
workdir /app

## copia o arquivo .jar compilado, para dentro da imagem
copy --from=build /builder/target/*.jar  app.jar

## define a porta que a imagem irá rodar no container
expose 8080

## Define o arquivo compilado que deve rodar na maquina
entrypoint ["java", "-jar", "app.jar"]