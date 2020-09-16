package com.emard.sigrhp.repository;

import com.emard.sigrhp.domain.UserExtra;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserExtra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserExtraRepository extends JpaRepository<UserExtra, Long> {

    Page<UserExtra> findByDeletedFalseOrderByUser_LastNameAscUser_FirstNameAsc(Pageable pageable);

}
