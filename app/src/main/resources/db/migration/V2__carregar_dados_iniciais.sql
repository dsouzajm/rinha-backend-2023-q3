--INSERT INTO pessoas (id, apelido, nome, nascimento) VALUES
--(gen_random_uuid(), 'maria', 'Maria Souza', '1995-05-20'),
--(gen_random_uuid(), 'joão', 'João Silva', '1998-11-30');

--INSERT INTO stacks (pessoa_id, stack_item) VALUES
--((SELECT id FROM pessoas WHERE apelido = 'maria'), 'Python'),
--((SELECT id FROM pessoas WHERE apelido = 'joão'), 'Go');