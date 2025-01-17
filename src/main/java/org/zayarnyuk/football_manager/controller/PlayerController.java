package org.zayarnyuk.football_manager.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zayarnyuk.football_manager.dto.PlayerDTO;
import org.zayarnyuk.football_manager.service.PlayerService;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping(value = "/create")
    @ResponseBody
    public PlayerDTO create(@RequestBody PlayerDTO player) {
        return playerService.createPlayer(player);
    }

    @GetMapping(value = "/readAll")
    @ResponseBody
    public List<PlayerDTO> read() {
        final List<PlayerDTO> players = playerService.getAllPlayers();
        return players;
    }

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<PlayerDTO> read(@PathVariable(name = "id") long id) {
        final  PlayerDTO player = playerService.findPlayerById(id);

        return player != null
                ? ResponseEntity.ok(player)
                : ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/edit/")
    @ResponseBody
    public PlayerDTO update( @RequestBody PlayerDTO player) {
        return playerService.updatePlayer(player);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        playerService.deletePlayerById(id);
        return ResponseEntity.ok().build();
    }

}

