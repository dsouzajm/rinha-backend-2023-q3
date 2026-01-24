CREATE EXTENSION IF NOT EXISTS pg_trgm;

-- UNLOGGED: Performance extrema de escrita
CREATE UNLOGGED TABLE pessoas (
    id UUID PRIMARY KEY,
    apelido VARCHAR(32) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    nascimento DATE NOT NULL,
    stack TEXT, -- Armazena a lista de stacks como uma string única (ex: "java node")

    -- Coluna gerada para busca textual ultra-rápida
    busca TEXT GENERATED ALWAYS AS (
        nome || ' ' || apelido || ' ' || COALESCE(stack, '')
    ) STORED
);

-- Índice GIN único cobrindo todos os campos de busca
CREATE INDEX IF NOT EXISTS idx_pessoas_busca_trgm ON pessoas USING GIN (busca gin_trgm_ops);