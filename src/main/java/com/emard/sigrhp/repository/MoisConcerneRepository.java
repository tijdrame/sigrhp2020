package com.emard.sigrhp.repository;

import com.emard.sigrhp.domain.MoisConcerne;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MoisConcerne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoisConcerneRepository extends JpaRepository<MoisConcerne, Long> {

    Page<MoisConcerne> findByDeletedFalseOrderByCode(Pageable pageable);

}
