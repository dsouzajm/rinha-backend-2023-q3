-- Criação da tabela para armazenar dados de pessoas
CREATE TABLE pessoas (
    id UUID PRIMARY KEY,
    apelido VARCHAR(32) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    nascimento DATE NOT NULL,
    stack TEXT[]
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;