package br.com.dsouzajm.repository;

import br.com.dsouzajm.entity.PessoaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PessoaRepository extends JpaRepository<PessoaEntity, UUID> {
    @Query("SELECT DISTINCT p FROM PessoaEntity p LEFT JOIN p.stacks s WHERE " +
            "LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(p.apelido) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(s.stackItem) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<PessoaEntity> findByTermo(@Param("termo") String termo, Pageable pageable);

    /*@Query(
            value = "SELECT DISTINCT p.* FROM pessoas p " +
                    "LEFT JOIN stacks s ON p.id = s.pessoa_id " +
                    "WHERE p.nome ILIKE CONCAT('%', :termo, '%') " +
                    "OR p.apelido ILIKE CONCAT('%', :termo, '%') " +
                    "OR s.stack_item ILIKE CONCAT('%', :termo, '%') " +
                    "LIMIT 50",
            nativeQuery = true
    )
    List<PessoaEntity> findByTermoComLimite(@Param("termo") String termo);*/

    @Query(
            value = "SELECT * FROM pessoas WHERE id IN (" +
                    "  SELECT id FROM pessoas WHERE nome ILIKE CONCAT('%', :termo, '%') OR apelido ILIKE CONCAT('%', :termo, '%')" +
                    "  UNION" +
                    "  SELECT pessoa_id FROM stacks WHERE stack_item ILIKE CONCAT('%', :termo, '%')" +
                    ") LIMIT 50",
            nativeQuery = true
    )
    List<PessoaEntity> findByTermoComLimite(@Param("termo") String termo);
}