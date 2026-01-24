-- Criação da tabela para armazenar dados de pessoas
-- UNLOGGED: Performance extrema de escrita (sem WAL), dados perdidos em crash do banco.
CREATE UNLOGGED TABLE pessoas (
    id UUID PRIMARY KEY,
    apelido VARCHAR(32) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    nascimento DATE NOT NULL
);

CREATE UNLOGGED TABLE stacks (
    id UUID PRIMARY KEY NOT NULL,
    pessoa_id UUID NOT NULL,
    stack_item VARCHAR(32) NOT NULL,
    CONSTRAINT fk_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoas(id)
);

-- Passo 1: Habilitar a extensão pg_trgm
CREATE EXTENSION IF NOT EXISTS pg_trgm;

-- Passo 2: Criar os índices GIN
CREATE INDEX IF NOT EXISTS idx_pessoas_busca_trgm ON pessoas USING GIN (apelido gin_trgm_ops, nome gin_trgm_ops);
CREATE INDEX IF NOT EXISTS idx_stacks_busca_trgm ON stacks USING GIN (stack_item gin_trgm_ops);