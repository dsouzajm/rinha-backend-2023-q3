package br.com.dsouzajm.repository;

import br.com.dsouzajm.entity.PessoaEntity;
import br.com.dsouzajm.entity.StackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface StackRepository extends JpaRepository<StackEntity, UUID> {
    @Query("SELECT s FROM StackEntity s WHERE LOWER(s.stackItem) LIKE LOWER(CONCAT('%', :termo, '%'))")
    public List<StackEntity> findByTermo(String termo);
}