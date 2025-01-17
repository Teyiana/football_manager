package org.zayarnyuk.football_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zayarnyuk.football_manager.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

}
