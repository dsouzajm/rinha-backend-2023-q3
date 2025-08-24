-- Criação da tabela para armazenar dados de pessoas
CREATE TABLE stacks (
    pessoa_id UUID NOT NULL,
    stack_item VARCHAR(100) NOT NULL,
    PRIMARY KEY (pessoa_id, stack_item)
);

CREATE TABLE pessoas (
    id UUID PRIMARY KEY,
    apelido VARCHAR(32) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    nascimento DATE NOT NULL
);