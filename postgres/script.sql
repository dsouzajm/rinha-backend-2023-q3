-- Criação da tabela para armazenar dados de pessoas
CREATE TABLE pessoas (
    id UUID PRIMARY KEY,
    apelido VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    nascimento DATE NOT NULL
);

CREATE TABLE stacks (
    id UUID PRIMARY KEY NOT NULL,
    pessoa_id UUID NOT NULL,
    stack_item VARCHAR(255) NOT NULL,
    CONSTRAINT fk_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoas(id)
);

-- Passo 1: Habilitar a extensão pg_trgm
-- Esta extensão fornece o suporte para a criação de índices de trigrama.
-- O Flyway irá garantir que este comando só é executado uma vez.
CREATE EXTENSION IF NOT EXISTS pg_trgm;

-- Passo 2: Criar os índices GIN (Generalized Inverted Index) com a classe de operador de trigrama
-- O GIN é o tipo de índice recomendado para este caso.

-- Índice para as colunas 'apelido' e 'nome' na tabela 'pessoas'
CREATE INDEX IF NOT EXISTS idx_pessoas_busca_trgm ON pessoas USING GIN (apelido gin_trgm_ops, nome gin_trgm_ops);

-- Índice para a coluna 'stack_item' na tabela 'stacks'
CREATE INDEX IF NOT EXISTS idx_stacks_busca_trgm ON stacks USING GIN (stack_item gin_trgm_ops);
