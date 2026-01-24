package br.com.dsouzajm.repository;

import br.com.dsouzajm.domain.Pessoa;
import br.com.dsouzajm.domain.Stack;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PessoaRepository {

    private final JdbcClient jdbcClient;
    
    // Agora inserimos tudo em uma única tabela
    private static final String SAVE_INSERT_PESSOA = "INSERT INTO pessoas (id, apelido, nome, nascimento, stack) VALUES (:id, :apelido, :nome, :nascimento, :stack)";
    
    private static final String FIND_BY_ID = """
            SELECT id, apelido, nome, nascimento, stack
            FROM pessoas
            WHERE id = :id
            """;

    // Busca ultra-rápida usando a coluna gerada e índice GIN
    private static final String FIND_BY_TERMO = """
            SELECT id, apelido, nome, nascimento, stack
            FROM pessoas
            WHERE busca ILIKE :termo
            LIMIT 50
            """;

    private static final String COUNT_PESSOAS = "SELECT count(id) FROM pessoas";

    public PessoaRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Transactional
    public Pessoa save(Pessoa pessoa) {
        if (pessoa.getId() == null) {
            pessoa.setId(UUID.randomUUID());
        }

        String stackString = null;
        if (pessoa.getStacks() != null && !pessoa.getStacks().isEmpty()) {
            // Concatena stacks com espaço para busca e armazenamento simples
            stackString = pessoa.getStacks().stream()
                    .map(Stack::getStack)
                    .collect(Collectors.joining(" "));
        }

        jdbcClient.sql(SAVE_INSERT_PESSOA)
                .param("id", pessoa.getId())
                .param("apelido", pessoa.getApelido())
                .param("nome", pessoa.getNome())
                .param("nascimento", pessoa.getNascimento())
                .param("stack", stackString)
                .update();

        return pessoa;
    }

    public Optional<Pessoa> findById(UUID id) {
        return jdbcClient.sql(FIND_BY_ID)
                .param("id", id)
                .query(this::extractPessoa);
    }

    public List<PessoaProjection> findByTermoComLimite(String termo) {
        String termoLike = new StringBuilder("%").append(termo).append("%").toString();
        
        return jdbcClient.sql(FIND_BY_TERMO)
                .param("termo", termoLike)
                .query((rs, rowNum) -> new PessoaProjection(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("apelido"),
                        rs.getString("nome"),
                        rs.getObject("nascimento", LocalDate.class),
                        stringToStackList(rs.getString("stack"))
                ))
                .list();
    }

    public long count() {
        return jdbcClient.sql(COUNT_PESSOAS)
                .query(Long.class)
                .single();
    }

    private Optional<Pessoa> extractPessoa(ResultSet rs) throws SQLException {
        if (rs.next()) {
            UUID id = UUID.fromString(rs.getString("id"));
            String apelido = rs.getString("apelido");
            String nome = rs.getString("nome");
            LocalDate nascimento = rs.getObject("nascimento", LocalDate.class);
            String stackStr = rs.getString("stack");
            
            List<Stack> stacks = new ArrayList<>();
            if (stackStr != null && !stackStr.isEmpty()) {
                for (String s : stackStr.split(" ")) {
                    stacks.add(new Stack(null, s));
                }
            }
            
            return Optional.of(new Pessoa(id, apelido, nome, nascimento, stacks));
        }
        return Optional.empty();
    }
    
    private List<String> stringToStackList(String stackStr) {
        if (stackStr == null || stackStr.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(stackStr.split(" "));
    }
}