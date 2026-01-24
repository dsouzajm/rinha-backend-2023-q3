# Stage 1: Build the native executable using GraalVM for Java 25
FROM ghcr.io/graalvm/native-image-community:25 AS build
WORKDIR /app

# Copy build files to leverage Docker cache
COPY pom.xml .
COPY .mvn/ .mvn
COPY mvnw .

# Make wrapper executable
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# --- COMANDO DE LIMPEZA NUCLEAR ---
# Procura e deleta qualquer arquivo native-image.properties dentro de src/
# Isso remove a configuração antiga que está causando o erro, esteja ela onde estiver.
RUN find src -name "native-image.properties" -type f -delete
# ----------------------------------

# Build native executable
# Using flags to handle potential compatibility issues with Undertow/Wildfly on newer Java versions
RUN ./mvnw -Pnative native:compile

# Intermediate stage for zlib (ARM64 specific)
FROM debian:bookworm-slim as zlib-provider

# Stage 2: Final minimal image
FROM gcr.io/distroless/base-debian12
WORKDIR /app

# Copy zlib for ARM64 support
COPY --from=zlib-provider /lib/aarch64-linux-gnu/libz.so.1 /lib/aarch64-linux-gnu/

# Copy the compiled binary
COPY --from=build /app/target/rinha-backend-2023-q3 .

EXPOSE 80
ENV SERVER_PORT=80
ENV SERVER_ADDRESS=0.0.0.0

ENTRYPOINT ["./rinha-backend-2023-q3"]