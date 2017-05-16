package com.mycompany.cpms.repository;

import com.mycompany.cpms.domain.MedicalHistory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MedicalHistory entity.
 */
@SuppressWarnings("unused")
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory,Long> {
    @Query("select medicalHistory from MedicalHistory medicalHistory where medicalHistory.user.login = ?#{principal.username}")
    List<MedicalHistory> findByUserIsCurrentUser();
}
