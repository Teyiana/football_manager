package org.zayarnyuk.football_manager.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zayarnyuk.football_manager.dto.PlayerDTO;
import org.zayarnyuk.football_manager.entity.Player;
import org.zayarnyuk.football_manager.repository.PlayerRepository;
import org.zayarnyuk.football_manager.repository.TeamRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;


    public List<PlayerDTO> getAllPlayers() {
        List<Player> playersList = playerRepository.findAll();
        return playersList.stream().map(PlayerService::toDto).toList();
    }


    public PlayerDTO createPlayer(PlayerDTO playerDto) {
        if (playerDto.getPlayerId() > 0) {
            throw new IllegalArgumentException("Id must be not set");
        }
        if (playerDto.getPlayerName() == null || playerDto.getPlayerName().isEmpty()) {
            throw new IllegalArgumentException("Name must be set");
        }
        if (playerDto.getDateOfBirth() == null) {
            throw new IllegalArgumentException("Date of birth must be set");
        }
        if (playerDto.getStartWorkDate() == null) {
            throw new IllegalArgumentException("Experience must be set");
        }
        Player player = toEntity(playerDto);
        return toDto(playerRepository.save(player));
    }

    private Player toEntity(PlayerDTO playerDto) {
        Player player = new Player();
        player.setPlayerName(playerDto.getPlayerName());
        player.setDateOfBirth(playerDto.getDateOfBirth());
        player.setStartWorkDate(playerDto.getStartWorkDate());
        if (playerDto.getTeamId() != null) {
            player.setTeam(teamRepository.findById(playerDto.getTeamId()).orElseThrow());
        }
        return player;
    }

    public PlayerDTO findPlayerById(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id is not valid: id=" + id);
        }
        Player player = playerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Player not found, id: " + id));
        return toDto(player);
    }

    public void deletePlayerById(Long id) {
        if (playerRepository.findById(id).isPresent()) {
            playerRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Player not found, id: " + id);
        }
    }

    public PlayerDTO updatePlayer(PlayerDTO playerDto) {
        Player player = playerRepository.findById(playerDto.getPlayerId()).orElseThrow();
        if (playerDto.getPlayerName() != null) {
            player.setPlayerName(playerDto.getPlayerName());
        }
        if (playerDto.getDateOfBirth() != null) {
            player.setDateOfBirth(playerDto.getDateOfBirth());
        }
        if (playerDto.getStartWorkDate()  != null) {
            player.setStartWorkDate(playerDto.getStartWorkDate());
        }
        player = playerRepository.save(player);
        return toDto(player);
    }


    public static PlayerDTO toDto(Player entity) {
       if (entity == null) {
           return null;
       }
       PlayerDTO dto = new PlayerDTO();

         dto.setPlayerId(entity.getPlayerId());
         dto.setPlayerName(entity.getPlayerName());
         dto.setDateOfBirth(entity.getDateOfBirth());
         dto.setStartWorkDate(entity.getStartWorkDate());
         dto.setTeamId(entity.getTeam() != null ? entity.getTeam().getId() : null);
         return dto;
    }
}
