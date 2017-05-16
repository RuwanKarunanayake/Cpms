package com.mycompany.cpms.repository;

import com.mycompany.cpms.domain.Investigation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Investigation entity.
 */
@SuppressWarnings("unused")
public interface InvestigationRepository extends JpaRepository<Investigation,Long> {

    Page<Investigation> findByRecordingUserLogin(String currentUserLogin, Pageable pageable);
}
