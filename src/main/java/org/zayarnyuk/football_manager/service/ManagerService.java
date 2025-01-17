package org.zayarnyuk.football_manager.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.zayarnyuk.football_manager.entity.Player;
import org.zayarnyuk.football_manager.entity.Team;
import org.zayarnyuk.football_manager.repository.PlayerRepository;
import org.zayarnyuk.football_manager.repository.TeamRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;


@Service
public class ManagerService {

    private final PlayerRepository playerRepository;
     private final TeamRepository teamRepository;

    public ManagerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @Transactional
    public void transferPlayer(Long playerId, Long newTeamId) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new IllegalArgumentException("Player not found, id: " + playerId));
        Team oldTeam = player.getTeam();
        Team newTeam = teamRepository.findById(newTeamId).orElseThrow(() -> new IllegalArgumentException("Team not found, id: " + newTeamId));

        Period workingPeriod = getPeriod(player.getStartWorkDate());
        Period agePeriod = getPeriod(player.getDateOfBirth());

        BigDecimal transferCost = BigDecimal.valueOf(workingPeriod.getMonths() * 100000 / agePeriod.getYears());

        BigDecimal commission = transferCost.multiply(BigDecimal.valueOf(oldTeam.getCommissionRate() / 100));
        BigDecimal totalCost = transferCost.add(commission);

        if (newTeam.getBalance().compareTo(totalCost) < 0) {
            throw new IllegalArgumentException("Insufficient funds in the new team's account");
        }

        newTeam.setBalance(newTeam.getBalance().subtract(totalCost));
        oldTeam.setBalance(oldTeam.getBalance().add(totalCost));

        player.setTeam(newTeam);

        playerRepository.save(player);
        teamRepository.save(oldTeam);
        teamRepository.save(newTeam);

    }

    private Period getPeriod(Date startDate) {
        LocalDate startLocalDate = startDate.toLocalDate();
        LocalDate now = LocalDate.now();
        return Period.between(startLocalDate, now);
    }



}
