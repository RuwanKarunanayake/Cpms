package com.mycompany.cpms.repository;

import com.mycompany.cpms.domain.Drug;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Drug entity.
 */
@SuppressWarnings("unused")
public interface DrugRepository extends JpaRepository<Drug,Long> {

}
