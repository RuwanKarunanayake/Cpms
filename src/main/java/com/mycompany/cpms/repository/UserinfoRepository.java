package com.mycompany.cpms.repository;

import com.mycompany.cpms.domain.Userinfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Userinfo entity.
 */
@SuppressWarnings("unused")
public interface UserinfoRepository extends JpaRepository<Userinfo,Long> {
    @Query("select userinfo from Userinfo userinfo where userinfo.user.login = ?#{principal.username}")
    List<Userinfo> findByUserIsCurrentUser();
}
