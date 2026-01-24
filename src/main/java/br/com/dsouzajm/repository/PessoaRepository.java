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

@Repository
public class PessoaRepository {

    private final JdbcClient jdbcClient;
    
    private static final String SAVE_INSERT_PESSOA = "INSERT INTO pessoas (id, apelido, nome, nascimento) VALUES (:id, :apelido, :nome, :nascimento)";
    private static final String SAVE_INSERT_STACK = "INSERT INTO stacks (id, pessoa_id, stack_item) VALUES (:id, :pessoaId, :stackItem)";
    
    private static final String FIND_BY_ID = """
            SELECT p.id, p.apelido, p.nome, p.nascimento, s.stack_item
            FROM pessoas p
            LEFT JOIN stacks s ON s.pessoa_id = p.id
            WHERE p.id = :id
            """;

    // Otimização: UNION ALL é mais rápido que UNION pois não faz distinct/sort.
    // O filtro IN (...) já garante que não haverá IDs duplicados no resultado final.
    private static final String FIND_BY_TERMO = """
            SELECT p.id, p.apelido, p.nome, p.nascimento, 
                   (SELECT array_agg(s.stack_item) FROM stacks s WHERE s.pessoa_id = p.id) as stacks
            FROM pessoas p
            WHERE p.id IN (
                SELECT id FROM pessoas WHERE nome ILIKE :termo OR apelido ILIKE :termo
                UNION ALL
                SELECT pessoa_id FROM stacks WHERE stack_item ILIKE :termo
            )
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

        jdbcClient.sql(SAVE_INSERT_PESSOA)
                .param("id", pessoa.getId())
                .param("apelido", pessoa.getApelido())
                .param("nome", pessoa.getNome())
                .param("nascimento", pessoa.getNascimento())
                .update();

        if (pessoa.getStacks() != null && !pessoa.getStacks().isEmpty()) {
            for (Stack stack : pessoa.getStacks()) {
                jdbcClient.sql(SAVE_INSERT_STACK)
                        .param("id", UUID.randomUUID())
                        .param("pessoaId", pessoa.getId())
                        .param("stackItem", stack.getStack())
                        .update();
            }
        }

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
                        mapStacks(rs.getArray("stacks"))
                ))
                .list();
    }

    public long count() {
        return jdbcClient.sql(COUNT_PESSOAS)
                .query(Long.class)
                .single();
    }

    private Optional<Pessoa> extractPessoa(ResultSet rs) throws SQLException {
        Pessoa pessoa = null;
        List<Stack> stacks = new ArrayList<>();

        while (rs.next()) {
            if (pessoa == null) {
                String idStr = rs.getString("id");
                UUID id = idStr != null ? UUID.fromString(idStr) : null;
                
                String apelido = rs.getString("apelido");
                String nome = rs.getString("nome");
                LocalDate nascimento = rs.getObject("nascimento", LocalDate.class);
                
                pessoa = new Pessoa(id, apelido, nome, nascimento, stacks);
            }
            String stackItem = rs.getString("stack_item");
            if (stackItem != null) {
                stacks.add(new Stack(null, stackItem));
            }
        }

        return Optional.ofNullable(pessoa);
    }
    
    private List<String> mapStacks(java.sql.Array sqlArray) throws SQLException {
        if (sqlArray == null) {
            return Collections.emptyList();
        }
        String[] array = (String[]) sqlArray.getArray();
        return Arrays.asList(array);
    }
}