package com.emard.sigrhp.repository;

import com.emard.sigrhp.domain.Convention;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Convention entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConventionRepository extends JpaRepository<Convention, Long> {

    Page<Convention> findByDeletedFalseOrderByLibelle(Pageable pageable);

}
