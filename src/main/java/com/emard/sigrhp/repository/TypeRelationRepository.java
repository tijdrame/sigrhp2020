package com.emard.sigrhp.repository;

import com.emard.sigrhp.domain.TypeRelation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeRelationRepository extends JpaRepository<TypeRelation, Long> {
    
    Page<TypeRelation> findByDeletedFalseOrderByLibelle(Pageable pageable);

}
