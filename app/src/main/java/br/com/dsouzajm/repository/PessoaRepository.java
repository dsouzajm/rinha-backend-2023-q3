package br.com.dsouzajm.repository;

import br.com.dsouzajm.entities.PessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PessoaRepository extends JpaRepository<PessoaEntity, UUID> {
    @Query("SELECT p FROM PessoaEntity p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR LOWER(p.apelido) LIKE LOWER(CONCAT('%', :termo, '%'))")
    public List<PessoaEntity> findByTermo(String termo);
}