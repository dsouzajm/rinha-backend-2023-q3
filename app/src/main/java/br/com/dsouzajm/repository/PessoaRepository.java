package br.com.dsouzajm.repository;

import br.com.dsouzajm.entities.PessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<PessoaEntity, Long> {
}
