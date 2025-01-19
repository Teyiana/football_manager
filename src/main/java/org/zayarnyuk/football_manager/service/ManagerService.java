package org.zayarnyuk.football_manager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zayarnyuk.football_manager.dto.TransferRequest;
import org.zayarnyuk.football_manager.entity.Player;
import org.zayarnyuk.football_manager.entity.Team;
import org.zayarnyuk.football_manager.repository.PlayerRepository;
import org.zayarnyuk.football_manager.repository.TeamRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

@RequiredArgsConstructor
@Service
public class ManagerService {

    public static final int ONE_HUNDRED_THOUSAND = 100000;

    private final PlayerRepository playerRepository;
     private final TeamRepository teamRepository;


    public void transferPlayer(TransferRequest transferRequest) {
        Player player = playerRepository.findById(transferRequest.getPlayerId()).orElseThrow(() -> new IllegalArgumentException("Player not found, id: " + transferRequest.getPlayerId()));
        Team oldTeam = player.getTeam();
        Team newTeam = teamRepository.findById(transferRequest.getTeamId()).orElseThrow(() -> new IllegalArgumentException("Team not found, id: " + transferRequest.getTeamId()));

        Period workingPeriod = getPeriod(player.getStartWorkDate());
        Period agePeriod = getPeriod(player.getDateOfBirth());

        BigDecimal transferCost = BigDecimal.valueOf(workingPeriod.getMonths() * ONE_HUNDRED_THOUSAND / agePeriod.getYears());

        BigDecimal commission = transferCost.multiply(BigDecimal.valueOf(oldTeam.getCommissionRate() / 100));
        BigDecimal totalCost = transferCost.add(commission);

        if (newTeam.getBalance().compareTo(totalCost) < 0) {
            throw new IllegalArgumentException("Insufficient funds in the new team's account");
        }

        newTeam.setBalance(newTeam.getBalance().subtract(totalCost));
        oldTeam.setBalance(oldTeam.getBalance().add(totalCost));
        player.setTeam(newTeam);

        saveChanges(player, oldTeam, newTeam);
    }

    @Transactional
    protected void saveChanges(Player player, Team oldTeam, Team newTeam) {
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
