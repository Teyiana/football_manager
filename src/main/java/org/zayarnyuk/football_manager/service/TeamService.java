package org.zayarnyuk.football_manager.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zayarnyuk.football_manager.dto.TeamDTO;
import org.zayarnyuk.football_manager.entity.Player;
import org.zayarnyuk.football_manager.entity.Team;
import org.zayarnyuk.football_manager.repository.TeamRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public List<TeamDTO> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        return teams.stream().map(TeamService::toDto).toList();
    }

    public TeamDTO getTeamById(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id is not valid: id=" + id);
        }
        Team team = teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Team not found, id: " + id));
        return toDto(team);
    }

    public TeamDTO create(TeamDTO teamDto) {
        if (teamDto.getId() > 0) {
            throw new IllegalArgumentException("Id must be not set");
        }
        if (teamDto.getTeamName() == null || teamDto.getTeamName().isEmpty()) {
            throw new IllegalArgumentException("Name must be set");
        }
        Team entity = toEntity(teamDto);
        return toDto(teamRepository.save(entity));
    }

    public void deleteTeamById(Long id) {
        if (teamRepository.findById(id).isPresent()) {
            teamRepository.deleteById(id);
        }
    }

    public TeamDTO update(TeamDTO teamDto) {
        Team team = teamRepository.findById(teamDto.getId()).orElseThrow(() -> new IllegalArgumentException("Team not found, id: " + teamDto.getId()));
        if (teamDto.getTeamName() != null) {
            team.setTeamName(teamDto.getTeamName());
        }
        if (teamDto.getBalance() != null) {
            team.setBalance(teamDto.getBalance());
        }
        if (teamDto.getCommissionRate() > 0) {
            team.setCommissionRate(teamDto.getCommissionRate());
        }
        return toDto(teamRepository.save(team));
    }

    public static TeamDTO toDto(Team entity) {
        if (entity == null) {
            return null;
        }
        TeamDTO dto = new TeamDTO();
        dto.setId(entity.getId());
        dto.setTeamName(entity.getTeamName());
        List<Player> players = entity.getPlayers();
        if (players != null) {
            dto.setPlayers(players.stream().map(PlayerService::toDto).toList());
        }
        dto.setBalance(entity.getBalance());
        dto.setCommissionRate(entity.getCommissionRate());

        return dto;
    }

    private Team toEntity(TeamDTO teamDto) {
        Team entity = new Team();
        entity.setId(teamDto.getId());
        entity.setTeamName(teamDto.getTeamName());
        entity.setBalance(teamDto.getBalance());
        entity.setCommissionRate(teamDto.getCommissionRate());
        return entity;
    }
}
