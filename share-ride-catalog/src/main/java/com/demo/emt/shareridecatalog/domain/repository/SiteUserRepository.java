package com.demo.emt.shareridecatalog.domain.repository;

import com.demo.emt.shareridecatalog.domain.model.SiteUser;
import com.demo.emt.shareridecatalog.domain.model.ids.SiteUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteUserRepository extends JpaRepository<SiteUser, SiteUserId> {
    Optional<SiteUser> findByUsername(String username);
    Optional<SiteUser> findByUsernameAndPassword(String username,String password);
}
