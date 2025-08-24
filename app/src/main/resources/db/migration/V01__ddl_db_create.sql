-- Criação da tabela para armazenar dados de pessoas
CREATE TABLE pessoas (
    id UUID PRIMARY KEY,
    apelido VARCHAR(32) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    nascimento DATE NOT NULL
);

CREATE TABLE stacks (
    id UUID PRIMARY KEY NOT NULL,
    pessoa_id UUID NOT NULL,
    stack_item VARCHAR(100) NOT NULL,
    CONSTRAINT fk_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoas(id)
);