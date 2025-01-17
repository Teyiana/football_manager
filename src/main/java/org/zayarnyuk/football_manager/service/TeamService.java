package org.zayarnyuk.football_manager.service;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.zayarnyuk.football_manager.dto.PlayerDTO;
import org.zayarnyuk.football_manager.dto.TeamDTO;
import org.zayarnyuk.football_manager.entity.Player;
import org.zayarnyuk.football_manager.entity.Team;
import org.zayarnyuk.football_manager.repository.TeamRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeamService {
    private final TeamRepository teamRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamRepository.class);

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
        Team team = new Team();
        team.setTeamName(teamDto.getTeamName());
        team = teamRepository.save(team);
        teamDto.setId(team.getId());
        return teamDto;
    }

    public void deleteTeamById(Long id) {
        if (teamRepository.findById(id).isPresent()) {
            teamRepository.deleteById(id);
        }
    }

    public TeamDTO update(TeamDTO teamDto) {
        if (teamDto.getId() <= 0) {
            throw new IllegalArgumentException("Id must be set");
        }
        if (teamDto.getTeamName() == null || teamDto.getTeamName().isEmpty()) {
            throw new IllegalArgumentException("Name must be set");
        }
        Team team = new Team();
        team.setId(teamDto.getId());
        team.setTeamName(teamDto.getTeamName());
        team = teamRepository.save(team);
        return toDto(team);
    }


    public boolean existsById(long teamId) {
        if (teamId <= 0) {
            throw new IllegalArgumentException("Id is not valid: id=" + teamId);
        }
        return teamRepository.existsById(teamId);
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
}
