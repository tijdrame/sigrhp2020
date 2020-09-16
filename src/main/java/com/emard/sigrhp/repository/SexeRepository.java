package com.emard.sigrhp.repository;

import com.emard.sigrhp.domain.Sexe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Sexe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SexeRepository extends JpaRepository<Sexe, Long> {

    Page<Sexe> findByDeletedFalseOrderByLibelle(Pageable pageable);

}
