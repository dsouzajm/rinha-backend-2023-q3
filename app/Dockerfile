# Stage 1: Build the native executable using GraalVM
FROM ghcr.io/graalvm/native-image-community:21 AS build
WORKDIR /app

# Copia os arquivos de build para aproveitar o cache de dependências do Docker
COPY pom.xml . 
COPY .mvn/ .mvn
COPY mvnw .

# Torna o wrapper executável
RUN chmod +x mvnw

# Baixa as dependências (será cacheado se o pom.xml não mudar)
RUN ./mvnw dependency:go-offline

# Copia o código-fonte
COPY app/src ./src

# Constrói o executável nativo ativando o perfil 'native'
# O executável será criado em /app/target/
RUN ./mvnw -Pnative -DskipTests native:compile

# Etapa intermediária para obter a biblioteca zlib para a arquitetura arm64
FROM debian:bookworm-slim as zlib-provider

# Stage 2: Create the final, minimal image
FROM gcr.io/distroless/base-debian12
WORKDIR /app

# Copia a biblioteca zlib da etapa intermediária para o local correto (aarch64) na imagem final
COPY --from=zlib-provider /lib/aarch64-linux-gnu/libz.so.1 /lib/aarch64-linux-gnu/
COPY --from=build /app/target/rinha-backend-2023-q3 .
EXPOSE 80
ENTRYPOINT ["./rinha-backend-2023-q3"]