package org.zayarnyuk.football_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zayarnyuk.football_manager.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

}
