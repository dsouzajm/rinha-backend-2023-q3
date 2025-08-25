package br.com.dsouzajm.repository;

import br.com.dsouzajm.entity.PessoaEntity;
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
}