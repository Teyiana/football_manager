package org.zayarnyuk.football_manager.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zayarnyuk.football_manager.dto.PlayerDTO;
import org.zayarnyuk.football_manager.service.PlayerService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public PlayerDTO create(@RequestBody PlayerDTO player) {
        return playerService.createPlayer(player);
    }

    @GetMapping(value = "/readAll")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public List<PlayerDTO> read() {
        final List<PlayerDTO> players = playerService.getAllPlayers();
        return players;
    }

    @GetMapping(value = "/read/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PlayerDTO> read(@PathVariable(name = "id") long id) {
        final  PlayerDTO player = playerService.findPlayerById(id);

        return player != null
                ? ResponseEntity.ok(player)
                : ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/edit")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseBody
    public PlayerDTO update(@RequestBody PlayerDTO player) {
        return playerService.updatePlayer(player);
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        playerService.deletePlayerById(id);
        return ResponseEntity.ok().build();
    }

}

