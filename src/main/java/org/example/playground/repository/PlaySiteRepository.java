package org.example.playground.repository;

import org.example.playground.domain.PlaySite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaySiteRepository extends JpaRepository<PlaySite, String> {
}
