package com.emard.sigrhp.repository;

import com.emard.sigrhp.domain.TypeAbsence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeAbsence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeAbsenceRepository extends JpaRepository<TypeAbsence, Long> {

    Page<TypeAbsence> findByDeletedFalseOrderByLibelle(Pageable pageable);

}
